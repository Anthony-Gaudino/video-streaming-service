package kr.tinywind.springbootstreaming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static kr.tinywind.springbootstreaming.config.Constants.VIDEO_ROOT;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private String decodePath(HttpServletRequest request, String prefix) throws UnsupportedEncodingException {
        return URLDecoder.decode(request.getRequestURI().replaceAll("[/]+", "/").substring(prefix.length()), "UTF-8");
    }

    @RequestMapping(value = {"", "show"})
    public String show() {
        return "show";
    }

    @RequestMapping("video-random-accessible/**")
    public ModelAndView videoRandomAccessible(HttpServletRequest request) throws UnsupportedEncodingException {
        return new ModelAndView("streamView", "movieName", decodePath(request, "/video-random-accessible/"));
    }

    @RequestMapping("video/**")
    public ResponseEntity<InputStreamResource> video(HttpServletRequest request)
            throws UnsupportedEncodingException, FileNotFoundException {
        final String decodeString = decodePath(request, "/video/");
        File file = new File(VIDEO_ROOT + decodeString);
        logger.info("DOWNLOAD " + file.getAbsolutePath());
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }
}
