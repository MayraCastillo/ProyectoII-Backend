FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
ADD ./target/config-service-0.0.1-SNAPSHOT.jar config-service.jar
ENTRYPOINT ["java" ,"-jar", "/config-service.jar"] 