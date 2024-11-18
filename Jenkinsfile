pipeline {
    agent any
    
    stages {
        stage('Clone Repository') {
            steps {
                // Clona el repositorio
                git branch: 'main', url: 'https://github.com/ArqProyecto/ArqSoftware.git'
            }
        }
        stage('Run Tests') {
            steps {
                // Ejecuta pruebas (ajusta el comando según tu proyecto)
                sh 'mvn test' // Cambia esto si no usas Maven
            }
        }
        stage('Build') {
            steps {
                // Construye el proyecto
                sh 'mvn package' // Cambia esto según el sistema de construcción
            }
        }
        stage('Deploy') {
            steps {
                // Simula el despliegue
                echo 'Desplegando aplicación...'
            }
        }
    }
}
