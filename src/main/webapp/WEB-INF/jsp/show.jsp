<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spring video streaming service</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="https://cdn.plyr.io/2.0.18/plyr.css"/>"/>
    </head>
    <body>
        <div id="plyr-container">
            <video poster="/video/poster.jpg" controls crossorigin>
            <!-- Video files -->
            <source src="/video-random-accessible/video.mp4"  type="video/mp4">
            <source src="/video-random-accessible/video.webm" type="video/webm">
        
            <!-- Text track file -->
            <track kind="captions" label="English" srclang="en" src="/video/captions.vtt" default>
        
            <!-- Fallback for browsers that don't support the <video> element -->
            <a href="/video/video.mp4" download>Download</a>
          </video>
        </div>
    
        <div id="scripts">
            <script type="text/javascript" src="<c:url value="https://cdn.plyr.io/2.0.18/plyr.js"/>"></script>
            
            <script type="text/javascript">
                document.addEventListener("DOMContentLoaded", function(e) { 
                    var plyrContainer = document.querySelector('#plyr-container');
                    
                    var plyrInstances = plyr.setup(plyrContainer);
                    
                    // You can also use the source method to change the player on the
                    // fly. Uncomment code bellow to try it.
                    /*plyrInstances[0].source({
                        type:       'video',
                        title:      'Example title',
                        sources: [{
                            src:    '/video-random-accessible/video.mp4',
                            type:   'video/mp4'
                        },
                        {
                            src:    '/video-random-accessible/video.webm',
                            type:   'video/webm'
                        }],
                        poster:     '/video/poster.jpg',
                        tracks:     [{
                            kind:   'captions',
                            label:  'English',
                            srclang:'en',
                            src:    '/video/captions.vtt',
                            default: true
                        }]
                    });*/
                });
            </script>
        </div>
    </body>
</html>