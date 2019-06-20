FROM rowanto/docker-java8-mvn-nodejs-npm

WORKDIR /code

ADD pom.xml /code/pom.xml

RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /code/src

WORKDIR /code

CMD ["mvn", "test"]