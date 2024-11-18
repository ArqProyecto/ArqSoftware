# Usa la imagen base de OpenJDK 22
FROM openjdk:22-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml al directorio de trabajo del contenedor
COPY ./pom.xml ./

# Copiar el directorio src (código fuente) al contenedor
COPY ./src ./src

# Ejecutar Maven para compilar el proyecto y generar el archivo .jar
RUN mvn clean install -DskipTests

# Copiar el archivo .jar generado al contenedor
COPY target/sistema-gestion-inmobiliaria-0.0.1-SNAPSHOT.jar /app/sistema-gestion-inmobiliaria.jar

# Exponer el puerto en el que la aplicación escuchará (si es necesario)
EXPOSE 8088

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "/app/sistema-gestion-inmobiliaria.jar"]
