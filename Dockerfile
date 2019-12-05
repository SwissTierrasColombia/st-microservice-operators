FROM openjdk:12

VOLUME /tmp

ADD ./target/st-microservice-operators-0.0.1-SNAPSHOT.jar st-microservice-operators.jar

EXPOSE 8080

ENTRYPOINT java -jar /st-microservice-operators.jar