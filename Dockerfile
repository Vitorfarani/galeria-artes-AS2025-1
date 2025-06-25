FROM openjdk:21
VOLUME /tmp
COPY target/artes-0.0.1-SNAPSHOT.jar app.jar
CMD java -Dserver.port=${PORT:-8080} -jar /app.jar