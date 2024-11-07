FROM alpine
RUN apk add --update openjdk17
WORKDIR /java
COPY Main.java .
RUN javac Main.java
CMD java Main