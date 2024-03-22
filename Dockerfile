FROM openjdk:11
LABEL authors="augustoribeirodev"

EXPOSE 8080

COPY /target/sales-management-0.0.1-SNAPSHOT.jar /app/sales_management.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/sales_management.jar"]