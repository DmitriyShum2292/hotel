FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/hotels-1.0.3-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"] 
