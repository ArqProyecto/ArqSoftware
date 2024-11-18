# Usar una imagen base con OpenJDK 22
FROM openjdk:22-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y el código fuente de GitHub al contenedor
COPY ./pom.xml ./ 
COPY ./src ./src

# Ejecutar el comando para compilar el .jar usando Maven
RUN mvn clean install -DskipTests

# Copiar el .jar generado al contenedor
COPY ./target/sistema-gestion-inmobiliaria-0.0.1-SNAPSHOT.jar /app/sistema-gestion-inmobiliaria.jar

# Exponer el puerto en el que tu aplicación escuchará
EXPOSE 8088

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "sistema-gestion-inmobiliaria.jar"]
