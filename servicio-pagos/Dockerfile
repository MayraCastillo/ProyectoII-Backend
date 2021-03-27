FROM openjdk:11
VOLUME /tmp
EXPOSE 8093
ADD ./target/servicio-pagos-0.0.1-SNAPSHOT.jar servicio-pagos.jar
ENTRYPOINT ["java" ,"-jar", "/servicio-pagos.jar"] 