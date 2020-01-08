FROM maven:3.3-jdk-8 AS build-env

COPY . /app
COPY settings.xml /root/.m2/settings.xml
WORKDIR /app
RUN mvn clean package

FROM jboss/wildfly

COPY --from=build-env /app/target/LSD-frontend.war /opt/jboss/wildfly/standalone/deployments/LSD-frontend.war
EXPOSE 8080