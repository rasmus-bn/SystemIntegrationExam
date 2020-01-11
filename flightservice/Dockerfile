# FROM maven:3.3-jdk-8 AS build-env

# COPY . /app
# COPY settings.xml /root/.m2/settings.xml
# WORKDIR /app
# RUN mvn clean compile
# RUN mvn clean package


# FROM tomcat:9-jdk8
# COPY --from=build-env /app/target/flightservice-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/LSD-frontend.war



# FROM tomcat:9-jdk8
# COPY ./target/flightservice-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/LSD-frontend.war

# EXPOSE 6565

# CMD [ "catalina.sh", "run" ]

FROM maven:3.6.3-jdk-8 AS build-env

COPY . /app
COPY settings.xml /root/.m2/settings.xml
WORKDIR /app
RUN mvn clean compile
RUN mvn clean package
EXPOSE 6565

CMD "mvn" "spring-boot:run"