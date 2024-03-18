FROM openjdk:17

RUN mkdir -p /app && \
    chown 1001:0 /app && \
    rm -rf /var/lib/apt/lists/*

ENV SERVER__PORT="80"

USER 1001

COPY target/cinephilia-payment-service-0.0.1-SNAPSHOT.jar /app/cinephilia-payment-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/cinephilia-payment-service-0.0.1-SNAPSHOT.jar"]
