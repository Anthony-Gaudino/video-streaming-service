# spring-boot-streaming
Spring Boot, video streaming, Plyr


![capture](https://raw.githubusercontent.com/Anthony-Gaudino/video-streaming-service/master/capture.jpg "Screenshot of Project")

# About
This is a simplified version of https://github.com/tinywind/video-streaming-service.

All database related code was removed, and the self coded video player is replaced by Plyr, the project is updated to Spring Boot 2.0.0 and Java 9, many dependencies were removed, other minor changes.

# Usage
* Edit **Constants.java**, change `String path` to a directory containing the served files
* Edit **show.jsp**, change the `poster.jpg`, `video.mp4`, `video.webm` and `captions.vtt` to whatever your files names are.
* Install jdk1.9
* On the project root run `$ ./mvnw install`
* Then run: `$ ./mvnw spring-boot:run`
* On a web browser, visit `http://localhost:8080`

# References
* http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle
* http://millky.com/@origoni/post/1100
* http://march3samwuli.tistory.com/entry/Step-1-%EC%9D%8C%EC%95%85-%EC%8A%A4%ED%8A%B8%EB%A6%AC%EB%B0%8D-%EC%84%9C%EB%B2%84Spring-boot-%EC%95%B1Android%EC%9D%84-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EC%9E%90
* http://aodis.egloos.com/5962812
* http://www.w3schools.com/html/html5_video.asp
* https://msdn.microsoft.com/ko-kr/library/hh924823(v=vs.85).aspx
* http://gingertech.net/2009/08/19/jumping-to-time-offsets-in-videos/ 
* https://ko.wikipedia.org/wiki/HTML5_%EB%B9%84%EB%94%94%EC%98%A4
