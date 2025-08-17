# Etapa 1: build
# Imagen base del jdk de Temurin (Java 21, Eclipse Temurin)
FROM eclipse-temurin:21-jdk-jammy AS build

# Directorio de la app
WORKDIR /app

# Copiar archivos de maven
COPY ./pom.xml /app/pom.xml
COPY ./.mvn /app/.mvn
COPY ./mvnw /app/mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

# Copiar código fuente
COPY ./src /app/src

# Compilar la aplicación (sin ejecutar tests)
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jdk-jammy AS runtime

# Directorio de imagen final
WORKDIR /app

# Copiar solo el .jar de la etapa build
COPY --from=build /app/target/PetShere-0.0.1-SNAPSHOT.jar /app/app.jar

# Puerto de la app
EXPOSE 8080

# Comando para ejecutar la aplicación compilada
ENTRYPOINT ["java", "-jar", "app.jar"]
