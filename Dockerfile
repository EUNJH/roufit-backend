FROM openjdk:17-jdk-oracle
WORKDIR /app
COPY ./build/libs/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]