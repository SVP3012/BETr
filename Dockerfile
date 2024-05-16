# FROM ubuntu:latest

# # Install Java
# RUN apt-get update && \
#     apt-get install -y default-jre && \
#     apt-get clean; 
 
# # Set Java Home environment variable (optional)
# ENV JAVA_HOME /usr/lib/jvm/java-1.11.0-openjdk-amd64

# COPY target/betr_backend-1.0-SNAPSHOT.jar /app/betr_backend-1.0-SNAPSHOT.jar
# CMD sed -i 's/\r$//' /app/calculator.sh

FROM openjdk
COPY ./target/betr_backend-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "betr_backend-0.0.1-SNAPSHOT.jar"]
