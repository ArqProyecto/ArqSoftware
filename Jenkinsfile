pipeline {
    agent any
    tools {
        maven 'Maven_Home' // Nombre de la herramienta Maven configurada en Jenkins
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/ArqProyecto/ArqSoftware.git'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Cambia al subdirectorio donde está el pom.xml
                    withSonarQubeEnv('SonarQube') {
                        bat 'mvn clean verify sonar:sonar' // Ejecuta el análisis de SonarQube
                    }
                }
            }
        }
        stage('Build') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Cambia al subdirectorio donde está el pom.xml
                    bat 'mvn clean package' // Construye el proyecto
                }
            }
        }
        stage('Generate Allure Report') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Cambia al subdirectorio donde están las pruebas
                    allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                }
            }
        }
        stage('Docker Build and Deploy') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Cambia al subdirectorio para construir y desplegar
                    echo 'Docker Build and Deploy started'
                    script {
                        // Nombre de la imagen Docker
                        def imageName = 'sistema-gestion-inmobiliaria'
                        
                        // Construir la imagen Docker
                        bat "docker build -t ${imageName} ."
                        
                        // Ejecutar el contenedor en Docker
                        bat "docker run -d -p 8082:8082 --name ${imageName}_container ${imageName}"
                    }
                }
            }
        }
    }
}
