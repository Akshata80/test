FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/new-demo-1.0-SNAPSHOT.jar /app/

EXPOSE 8000

CMD ["java", "-jar", "new-demo-1.0-SNAPSHOT.jar"]