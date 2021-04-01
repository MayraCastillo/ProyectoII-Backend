FROM openjdk:11
VOLUME /tmp
EXPOSE 8099
ADD ./target/registry-service-0.0.1-SNAPSHOT.jar registry-service.jar
ENTRYPOINT ["java" ,"-jar", "/registry-service.jar"] 