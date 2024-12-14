FROM gradle:jdk21-alpine AS base

WORKDIR /app

COPY order-service/build.gradle ./
COPY order-service/settings.gradle ./
COPY order-service/src/ ./src/

RUN gradle clean build -x test --parallel


FROM base AS build

WORKDIR /application

COPY --from=base /app/build/libs/order-service-1.0.jar output/app.jar
COPY --from=base /app/src/main/resources/application.properties config/

EXPOSE 9097

ENTRYPOINT ["java", "-jar", "output/app.jar"]