pipeline {
    agent any
    tools {
        maven 'Maven_Home'
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/ArqProyecto/ArqSoftware.git'
            }
        }
        stage('Run Tests') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') {
                    bat 'mvn test'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        bat 'mvn clean verify sonar:sonar'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') {
                    bat 'mvn clean package'
                }
            }
        }
        stage('Generate Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
        stage('Docker Build and Deploy') {
            steps {
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
