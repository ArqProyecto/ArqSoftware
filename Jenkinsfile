pipeline {
    agent any
    tools {
        maven 'Maven_Home' // Asegúrate de usar el mismo nombre configurado en Jenkins
    }
    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // Usamos la herramienta Git instalada
                    git branch: 'main', url: 'https://github.com/ArqProyecto/ArqSoftware.git'
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    // Ejecutar comandos de prueba compatibles con Windows
                    bat 'echo Running Tests...'
                    bat 'mvn test' // Reemplázalo según tu herramienta de pruebas
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    // Construcción del proyecto
                    bat 'echo Building Project...'
                    bat 'mvn package' // Reemplázalo según tu herramienta de construcción
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Implementación
                    bat 'echo Deploying Application...'
                    // Agrega aquí los pasos necesarios para el despliegue
                }
            }
        }
    }
}
