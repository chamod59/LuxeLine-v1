# Use a base image with Java 17 and Maven for building
FROM maven:3.9.6-openjdk-17-slim AS build

# Set the working directory inside the build stage container
WORKDIR /app

# Copy the Maven project files (pom.xml) and source code
# This step caches dependencies. If pom.xml doesn't change, Maven won't re-download.
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application using Maven
# -DskipTests: Skips running tests to speed up the build for deployment
RUN mvn clean package -DskipTests

# --- Second Stage: Create a smaller runtime image ---
FROM openjdk:17-jdk-slim

# Set the working directory for the final application
WORKDIR /app

# Copy the built JAR file from the 'build' stage
# Assuming your Spring Boot application generates a JAR in 'target/' named like 'your-artifact-id-version.jar'
# The 'target/*.jar' wildcard is common, but adjust if your JAR has a very specific name.
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Command to run your Spring Boot application
# This will execute the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]