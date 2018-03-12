package kr.tinywind.springbootstreaming.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import static kr.tinywind.springbootstreaming.config.Constants.VIDEO_ROOT;

@Component("streamView")
public class StreamView extends AbstractView
{
    private static final Logger logger = LoggerFactory.getLogger(StreamView.class);

    @Override
    protected void renderMergedOutputModel( Map<String, Object> map,
                                            HttpServletRequest  request,
                                            HttpServletResponse response
                                           ) throws Exception
    {
        final File              movieFIle = new File(VIDEO_ROOT + (String) map.get("movieName"));
        final RandomAccessFile randomFile = new RandomAccessFile(movieFIle, "r");

        long    rangeStart = 0;      //Starting position of request scope
        long    rangeEnd   = 0;      //End position of request scope
        boolean isPart     = false;  //True if partial request, false if total request

        // Use try ~ finally to close randomFile
        try {
            long movieSize = randomFile.length();         //Video file size
            String range   = request.getHeader("range");  //Read the range from the stream request scope, request header.
            logger.debug("range: {}", range);

            /* 
             * Depending on the browser, the range format is different. The default format is "bytes = {start} - {end}".
             * If range is null, reqStart is zero, and there is no end, this is the entire request.
             * Get the scope of the request.
             */
            if (range != null) {
              //For ease of processing, put the end value in the request range if there is no end value </ font>
                if (range.endsWith("-"))    range = range + (movieSize - 1);
                
                int idxm   = range.trim().indexOf("-");//"-" location
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd   = Long.parseLong(range.substring(idxm + 1));
                
                if (rangeStart > 0)    isPart = true;
            } else {  //If the range is null, the initial value is set to the full size of the video. Since it starts at 0, -1
                rangeStart = 0;
                rangeEnd   = movieSize - 1;
            }

            long partSize = rangeEnd - rangeStart + 1;  //Transfer file size
            logger.debug("accepted range: {}", rangeStart + "-" + rangeEnd + "/" + partSize + " isPart:" + isPart);
            
            response.reset();                        //Start transfer
            response.setStatus(isPart ? 206 : 200);  // Specify 200 as a return status code for all requests and 206 for partial requests
            response.setContentType("video/mp4");    // Specify mime type

            // Put the contents of the transmission in the head. Finally, put the full size of the file.
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", "" + partSize);

            OutputStream out = response.getOutputStream();
            randomFile.seek(rangeStart);  //Specify the transfer start position of the movie file

             /*
              * File transfer ... java io transfers 1 time byte is specified as int
              * For movie files, there are files that can not be processed as int type.
              * Implemented in 8kb so that it does not matter if the file size is large
              */
            int bufferSize = 8 * 1024;
            byte[] buf     = new byte[bufferSize];
            
            do {
                int block = partSize > bufferSize ? bufferSize : (int) partSize;
                int len   = randomFile.read(buf, 0, block);
                
                out.write(buf, 0, len);
                partSize -= block;
            } while (partSize > 0);
            logger.debug("sent " + movieFIle.getAbsolutePath() + " " + rangeStart + "-" + rangeEnd);
        } catch (IOException e) {
            //Cancel transmission if you close the browser during transmission or when you switch screens because you need to exit.
            //If you clicked the progressBar, the transfer is canceled because the request for the position you clicked is received again.
            logger.debug("Transmission canceled");
        } finally {
            randomFile.close();
        }
    }
}