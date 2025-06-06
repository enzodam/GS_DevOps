# Dockerfile.app

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY sos-climatech-api-main/pom.xml .
# Download dependencies to leverage Docker cache
RUN mvn dependency:go-offline
COPY sos-climatech-api-main/src ./src
RUN mvn package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy

# Create a non-root user and group
RUN groupadd -r appgroup && useradd --no-log-init -r -g appgroup appuser

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown appuser:appgroup app.jar

# Switch to the non-root user
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

