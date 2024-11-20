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
                        docker ps -a -q --filter "name=${containerName}" | findstr . && docker rm -f ${containerName} || echo "No container to remove"
                        """
                        
                        // Construir la imagen Docker
                        bat "docker build -t ${imageName} ."
                        
                        // Ejecutar el contenedor en Docker
                        bat "docker run -d -p 8082:8082 --name ${containerName} ${imageName}"
                    }
                }
            }
        }

    }
}
