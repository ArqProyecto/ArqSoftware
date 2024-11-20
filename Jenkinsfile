pipeline {
    agent any
    tools {
        maven 'Maven_Home' // Nombre de la herramienta Maven configurada en Jenkins
    }
    environment {
        SONAR_TOKEN = credentials('sonar-token')  // Aquí se usa el ID de la credencial que creaste
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
                    script {
                        withSonarQubeEnv('SonarQube') {
                            bat "mvn clean verify sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                        }
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
                dir('sistema-gestion-inmobiliaria/Backend') {
                    echo 'Docker Build and Deploy started'
                    script {
                        def imageName = 'sistema-gestion-inmobiliaria'
                        def containerName = "${imageName}_container"
                        
                        // Eliminar el contenedor si ya existe
                        bat """
                        docker ps -a -q --filter "name=${containerName}" | findstr . >nul && docker rm -f ${containerName} || (echo "No container to remove" & exit 0)
                        """
                        
                        // Construir la imagen Docker
                        try {
                            echo "Building Docker image: ${imageName}"
                            bat "docker build -t ${imageName} ."
                        } catch (Exception e) {
                            error "Failed to build Docker image: ${e.message}"
                        }
        
                        // Ejecutar el contenedor en Docker
                        try {
                            echo "Running Docker container: ${containerName}"
                            bat "docker run -d -p 8082:8082 --name ${containerName} ${imageName}"
                        } catch (Exception e) {
                            error "Failed to run Docker container: ${e.message}"
                        }
                    }
                }
            }
        }
    }
}
