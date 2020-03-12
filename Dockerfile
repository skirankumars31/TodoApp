# fetch basic image
FROM maven:3.6.3-jdk-11

ARG JAR_FILE=target/*.jar

# Add the jar file to the directory
COPY ${JAR_FILE} app.jar

#local application port
EXPOSE 8080

# Tell docker on how to start the spring boot application
ENTRYPOINT ["java","-jar","app.jar"]