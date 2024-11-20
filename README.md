# Sistema de Gestión Inmobiliaria

## Descripción del Proyecto

El **Sistema de Gestión Inmobiliaria** es una solución diseñada para facilitar la administración de propiedades, arrendamientos, pagos y usuarios. La aplicación está construida con **Java** utilizando **Spring Boot** en el backend y un **frontend web** que permite a los administradores gestionar la información de manera eficiente. 

Este sistema incluye funcionalidades como:
- Gestión de propiedades.
- Administración de usuarios y arrendamientos.
- Gestión de pagos y transacciones.
- Reportes e historial de actividades.

La arquitectura del sistema está basada en una **arquitectura por capas**, lo que permite una mayor escalabilidad y mantenibilidad. Se emplean prácticas de **Integración Continua (CI)** y **Despliegue Continuo (CD)** utilizando herramientas como **Jenkins**, **Docker**, y **SonarQube** para garantizar la calidad del código y la eficiencia en el despliegue.

## Tecnologías Utilizadas

- **Backend**: 
  - Java
  - Spring Boot
  - Maven
  
- **Frontend**: 
  - HTML, CSS, JavaScript
  
- **Base de Datos**: 
  - MySQL
  
- **Herramientas de CI/CD**:
  - Jenkins
  - Docker
  
- **Análisis de Calidad de Código**: 
  - SonarQube
  
- **Pruebas**:
  - JUnit
  - Allure
 
## Características del Proyecto

- **Arquitectura por Capas**: Separación de responsabilidades en capas de presentación, lógica de negocio y persistencia de datos.
- **Automatización de Pruebas**: Ejecución automática de pruebas unitarias, pruebas de integración y pruebas de carga.
- **Pipeline CI/CD**: Automatización del flujo de trabajo desde la construcción hasta el despliegue de la aplicación en entornos de pruebas y producción.
- **Contenedores Docker**: Empaque del servicio backend en contenedores Docker para facilitar el despliegue en cualquier entorno.
- **Monitoreo de Calidad de Código**: Uso de SonarQube para analizar el código en busca de vulnerabilidades y problemas de mantenimiento.
