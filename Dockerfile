# Stage 1: Build with Maven
FROM maven:latest as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-slim
WORKDIR /app
# Adjust the COPY path according to where the JAR file ends up after the Maven build.
# This assumes your Maven project builds a single JAR and places it in the "target" directory.
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
