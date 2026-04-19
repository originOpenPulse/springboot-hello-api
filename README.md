# springboot-hello-api

A simple Spring Boot hello world API example with Swagger, Docker, and Helm packaging.

## Run locally

```bash
mvn spring-boot:run
```

Then open `http://localhost:18081/hello`.

## Build

```bash
mvn clean package
```

## Container image

The sample Helm and Docker files default to `ghcr.io/originopenpulse/springboot-hello-api`.
Adjust image registry, credentials, and ingress host values for your own environment before deployment.
