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
        stage('Run Tests') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Cambiar al subdirectorio correcto
                    bat 'mvn test' // Ejecutar Maven desde el subdirectorio
                }
            }
        }
        stage('Build') {
            steps {
                dir('sistema-gestion-inmobiliaria/Backend') { // Asegúrate de estar en el subdirectorio correcto
                    bat 'mvn clean package' // Comando de construcción
                }
            }
        }
        stage('Deploy') {
            steps {
                // Agrega tu lógica de despliegue aquí
                echo 'Despliegue iniciado'
            }
        }
    }
}
