FROM gradle:jdk21-alpine AS base

WORKDIR /app

COPY service-registry/build.gradle ./
COPY service-registry/settings.gradle ./
COPY service-registry/src/ ./src/

RUN gradle clean build -x test --parallel


FROM base AS build

WORKDIR /application

COPY --from=base /app/build/libs/service-registry-1.0.jar output/app.jar
COPY --from=base /app/src/main/resources/application.properties config/

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "output/app.jar"]