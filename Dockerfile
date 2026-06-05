# ─── Stage 1: Build ───────────────────────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom primero para aprovechar la caché de capas de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/target/restock-web-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]