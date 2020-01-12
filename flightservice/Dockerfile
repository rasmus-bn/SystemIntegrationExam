FROM maven:3.6.3-jdk-8 AS build-env

COPY . /app
COPY settings.xml /root/.m2/settings.xml
WORKDIR /app
RUN mvn clean compile
RUN mvn clean package
EXPOSE 6565

CMD "mvn" "spring-boot:run"