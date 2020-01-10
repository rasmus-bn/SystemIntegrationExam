FROM maven:3.6.3-jdk-8 AS build-env

COPY . /app
WORKDIR /app
RUN mvn clean package

CMD "mvn" "spring-boot:run"