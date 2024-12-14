FROM gradle:jdk21-alpine AS base

WORKDIR /app

COPY payment-service/build.gradle ./
COPY payment-service/settings.gradle ./
COPY payment-service/src/ ./src/

RUN gradle clean build -x test --parallel


FROM base AS build

WORKDIR /application

COPY --from=base /app/build/libs/payment-service-1.0.jar output/app.jar
COPY --from=base /app/src/main/resources/application.properties config/

EXPOSE 9096

ENTRYPOINT ["java", "-jar", "output/app.jar"]