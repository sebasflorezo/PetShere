# Imagen base del jdk de Temurin (Java 21, Eclipse Temurin)
FROM eclipse-temurin:21-jdk-jammy

# Directorio de la app
WORKDIR /app

# Copia del .jar generado
COPY target/PetShere-0.0.1-SNAPSHOT.jar app.jar

# Puerto de la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
