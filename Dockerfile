# Use Amazon Corretto 17 as the base image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/shorturl-0.0.1-SNAPSHOT.jar urlShortner.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "urlShortner.jar"]