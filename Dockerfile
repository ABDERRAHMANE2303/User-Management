# Stage 1: Build the WAR with Maven and JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies (cache this step)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the WAR skipping tests for faster build; remove -DskipTests if you want tests
RUN mvn clean package -DskipTests

# Stage 2: Run the WAR in Tomcat 10 with JDK 21
FROM tomcat:10.1-jdk21

# Remove default webapps to keep container clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR built in the previous stage, rename it to ROOT.war for default app context
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Add a simple healthcheck (optional)
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:8080/ || exit 1

# Start Tomcat
CMD ["catalina.sh", "run"]
