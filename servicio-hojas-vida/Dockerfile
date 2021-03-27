FROM openjdk:11
VOLUME /tmp
EXPOSE 8092
ADD ./target/servicio-hojas-vida-0.0.1-SNAPSHOT.jar servicio-hojas-vida.jar
ENTRYPOINT ["java" ,"-jar", "/servicio-hojas-vida.jar"] 