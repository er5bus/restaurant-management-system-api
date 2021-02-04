FROM maven:3.6.3-jdk-8


LABEL MAINTAINER="Rami sfari <rami2sfari@gmail.com>"

COPY ./pom.xml /pom.xml

# Install Dependencies
#RUN ["mvn", "dependency:resolve"]


COPY . /usr/src/project

WORKDIR /usr/src/project

CMD ["mvn", "spring-boot:run", "-Drun.arguments=\"spring.profiles.active=UAT\""]
