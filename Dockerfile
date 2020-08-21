FROM openjdk:12

ARG XMX=1024m
ARG PROFILE=production

ENV XMX=$XMX
ENV PROFILE=$PROFILE

VOLUME /tmp

ADD ./target/st-microservice-operators-0.0.1-SNAPSHOT.jar st-microservice-operators.jar

EXPOSE 8080

ENTRYPOINT java -Xmx$XMX -jar /st-microservice-operators.jar --spring.profiles.active=$PROFILE