FROM maven:3.8.3-eclipse-temurin-17 as builder

ENV PORT 80
EXPOSE 80

WORKDIR /core

COPY core/pom.xml .
COPY core/src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk

COPY --from=builder /core/target/*.jar /api.jar

CMD ["java", "-Dserver.port=80", "-Dspring.profiles.active=prod", "-jar", "/api.jar"]