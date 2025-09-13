# BUILD
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package

#
# RELEASE
#
FROM ghcr.io/graalvm/graalvm-community:21 AS runtime
WORKDIR /app
COPY --from=build /build/target/*.jar ./app.jar
COPY docker-entrypoint.sh ./
ENTRYPOINT ["java","-jar","/app/app.jar"]
