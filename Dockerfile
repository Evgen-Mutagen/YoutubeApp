FROM openjdk:17
ADD target/youtubeAPI-0.5.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
