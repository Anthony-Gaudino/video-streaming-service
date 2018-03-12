package kr.tinywind.springbootstreaming.controller;

import static kr.tinywind.springbootstreaming.config.Constants.VIDEO_ROOT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

//import kr.tinywind.springbootstreaming.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private String decodePath(HttpServletRequest request, String prefix)
            throws UnsupportedEncodingException
    {
        return URLDecoder.decode(request.getRequestURI().replaceAll("[/]+", "/").substring(prefix.length()), "UTF-8");
    }



    /*
     * Used to show the JSP page.
     */
    @RequestMapping(value = {"", "show"})
    public String show() { return "show"; }



    /*
     * Used to stream videos.
     * The stream can be randomly acceded.
     * 
     * Example: http://localhost:8080/video-random-accessible/small.mp4
     */
    @RequestMapping("video-random-accessible/**")
    public ModelAndView videoRandomAccessible(HttpServletRequest request)
            throws UnsupportedEncodingException
    {
        return new ModelAndView( "streamView",
                                 "movieName",
                                 decodePath(request, "/video-random-accessible/")
                               );
    }



    /*
     * Used to download videos and other files.
     * 
     * Example: http://localhost:8080/video/video.mp4 starts download of video.mp4
     */
    @RequestMapping("video/**")
    public ResponseEntity<InputStreamResource> video(HttpServletRequest request)
            throws UnsupportedEncodingException, FileNotFoundException
    {
        final String      decodeString = decodePath(request, "/video/");
        final HttpHeaders headers      = new HttpHeaders();
              File        file         = new File(VIDEO_ROOT + decodeString);
              
        logger.info("DOWNLOAD " + file.getAbsolutePath());
        
        headers.add("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
        
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }
}