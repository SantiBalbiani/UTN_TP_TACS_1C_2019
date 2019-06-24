FROM rowanto/docker-java8-mvn-nodejs-npm

WORKDIR /code

ADD pom.xml /code/pom.xml

RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /code/src

WORKDIR /code

RUN ["mvn", "package", "-DskipTests"]

WORKDIR /code/target

EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "-Dspring.data.mongodb.host=mongodb_container", "findurplace_back-1.0-SNAPSHOT.jar"]
