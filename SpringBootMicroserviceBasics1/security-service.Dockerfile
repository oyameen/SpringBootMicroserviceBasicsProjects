FROM gradle:jdk21-alpine AS base

WORKDIR /app

COPY security-service/build.gradle ./
COPY security-service/settings.gradle ./
COPY security-service/src/ ./src/

RUN gradle clean build -x test --parallel


FROM base AS build

WORKDIR /application

COPY --from=base /app/build/libs/security-service-1.0.jar output/app.jar
COPY --from=base /app/src/main/resources/application.properties config/

EXPOSE 9098

ENTRYPOINT ["java", "-jar", "output/app.jar"]