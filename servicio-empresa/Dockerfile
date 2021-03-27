FROM openjdk:11
VOLUME /tmp
EXPOSE 8091
ADD ./target/servicio-empresa-0.0.1-SNAPSHOT.jar servicio-empresa.jar
ENTRYPOINT ["java" ,"-jar", "/servicio-empresa.jar"] 