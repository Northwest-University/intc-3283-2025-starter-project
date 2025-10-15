FROM maven:3.9.11-amazoncorretto-24 AS maven
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f /app/pom.xml clean package -DskipTests=true

FROM amazoncorretto:24
COPY --from=maven /app/target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]

EXPOSE 8080
