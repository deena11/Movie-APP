FROM java:8
VOLUME /tmp
ADD target/kafka-publish-server-0.0.1-SNAPSHOT.jar kafka-publish-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","kafka-publish-server-0.0.1-SNAPSHOT.jar"]