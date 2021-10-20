FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/hotels-1.1.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"] 
