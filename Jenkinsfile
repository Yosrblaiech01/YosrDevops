pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Récupération du code depuis Git..."
                git branch: 'main', url: 'https://github.com/Yosrblaiech01/YosrDevops.git'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Lancement des tests Maven..."
                sh "mvn test"
            }
        }

        stage('Build Artifact') {
            steps {
                echo " Création du livrable (JAR/WAR)..."
                sh "mvn clean package"
            }
        }
    }
}
