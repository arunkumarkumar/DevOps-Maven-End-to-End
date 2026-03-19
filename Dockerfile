FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/arunkumarkumar_DevOps-Maven-End-to-End-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
