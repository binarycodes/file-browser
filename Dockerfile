FROM debian:bookworm-slim

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

RUN apt-get update && apt-get install -y --no-install-recommends openjdk-17-jre-headless
RUN rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/*.jar /app/

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar /app/*.jar"]
