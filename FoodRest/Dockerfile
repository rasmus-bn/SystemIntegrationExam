FROM maven:3.6.3-jdk-8 AS build-env

COPY . /app
WORKDIR /app
RUN mvn clean compile
RUN mvn clean package
EXPOSE 5009

CMD "mvn" "spring-boot:run"