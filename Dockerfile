# Use a Maven image to build the application
FROM openjdk:21-slim as builder
WORKDIR /build
COPY pom.xml .
COPY src src/
RUN mvn package -DskipTests

# Use the OpenJDK 21 image for the application runtime
FROM openjdk:21-slim
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]