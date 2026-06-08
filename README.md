# Restock API

Backend de la plataforma Restock, desarrollado con **Spring Boot**, aplicando principios de **Domain-Driven Design (DDD)** e integrado con **MongoDB** como base de datos principal y **Redis** como capa de cache de alto rendimiento.

## Prerrequisitos

Antes de comenzar, asegurate de tener instalado lo siguiente en tu maquina:
* **Java Development Kit (JDK) 21** (Se recomienda la distribucion Eclipse Temurin).
* **Apache Maven 3.9+** (O usa el wrapper `./mvnw` incluido).
* **Docker Desktop** (Crucial para levantar las dependencias locales sin instalar nada nativo).
* Un IDE con soporte para Java (Se recomienda **IntelliJ IDEA** con el plugin de Lombok habilitado).

---

## Configuracion del Entorno Local

A partir de la incorporacion de Redis para la optimizacion de consultas, el backend requiere que tanto MongoDB como Redis esten corriendo para poder iniciar en verde. No es necesario instalarlos directamente en Windows; usaremos Docker.

### 1. Levantar las Dependencias con Docker

Abre tu terminal (PowerShell o CMD) y ejecuta los siguientes comandos para crear y encender los contenedores de soporte:

```bash
# 1. Levantar la base de datos MongoDB (en el puerto 27017)
docker run -d --name mongo-local -p 27017:27017 mongo:7

# 2. Levantar el servidor de cache Redis (en el puerto 6379)
docker run -d --name redis-local -p 6379:6379 redis:7-alpine
```

> **Nota:** Si los contenedores ya existen y estan apagados, solo debes despertarlos ejecutando `docker start mongo-local redis-local`.

### 2. Variables de Entorno Requeridas

El archivo `src/main/resources/application.properties` esta configurado para leer variables de entorno con valores por defecto para local. Sin embargo, para que el modulo de autenticacion (JWT) funcione sin lanzar excepciones de seguridad, debes asegurarte de configurar una clave JWT lo suficientemente larga en las variables de entorno de tu IDE o sistema:

* `JWT_SECRET`: Una cadena de texto de al menos 32 caracteres (256 bits). Ejemplo: `EsteEsUnSecretoSuperSeguroYMuyLargoParaJWTRestock2026`

---

## Ejecucion del Proyecto

### Desde el IDE (Recomendado para Desarrollo)

1. Abre el proyecto en IntelliJ IDEA.
2. Deja que Maven descargue todas las dependencias del `pom.xml`.
3. Asegurate de que `mongo-local` y `redis-local` esten corriendo en Docker (`docker ps`).
4. Ejecuta la clase principal `RestockWebServiceApplication`.

### Desde la Linea de Comandos

```bash
# Compilar y correr la aplicacion
./mvnw spring-boot:run
```

El servidor iniciara por defecto en el puerto **8080**.

---

## Documentacion de la API (Swagger UI)

Una vez que el backend este corriendo, puedes acceder a la interfaz interactiva de Swagger para probar todos los endpoints implementados:
http://localhost:8080/swagger-ui.html

---

## Manejo de la Cache (Redis) en Desarrollo

Si estas haciendo pruebas con endpoints que utilizan `@Cacheable` (como la consulta de insumos personalizados) y necesitas limpiar la memoria para forzar una consulta fresca a MongoDB, puedes vaciar Redis desde la terminal:

```bash
# Entrar al cliente de Redis dentro del contenedor
docker exec -it redis-local redis-cli

# Borrar todas las llaves guardadas en cache
127.0.0.1:6379> FLUSHALL
```

---

## Integracion Continua (CI/CD)

Este repositorio cuenta con un pipeline automatizado mediante GitHub Actions (`.github/workflows/backend.yml`).

Cada vez que realices un `git push` a las ramas `main` o `develop`, el flujo se encargara de:

1. Validar y compilar el codigo con Maven.
2. Construir la imagen Docker inmutable.
3. Publicar la imagen en el registro oficial de Docker Hub con los tags `:latest` y el SHA del commit correspondiente.

Para desplegar la infraestructura completa (Backend + DB + Cache) de manera integrada, consulta el repositorio de orquestacion: `restock-infra`.