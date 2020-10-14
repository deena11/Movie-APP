FROM java:8
VOLUME /tmp
ADD target/movie-inventory-service-0.0.1-SNAPSHOT.jar movie-inventory-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","movie-inventory-service-0.0.1-SNAPSHOT.jar"]