pipeline {
    agent any

    environment {
        M2_HOME = "/usr/share/maven"
        PATH = "${env.M2_HOME}/bin:${env.PATH}"
        DOCKERHUB_CREDENTIALS = 'dockerhub-yosrblaiech'
        IMAGE_NAME = 'yosrblaiech/yosrdevops'
    }

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/Yosrblaiech01/YosrDevops.git', branch: 'main'
            }
        }

       /* stage('Tests unitaires') {
            steps {
                sh 'mvn test -Dmaven.test.skip=true'
            }
        }*/
        stage('Tests unitaires') {
    steps {
        sh 'mvn test -Dspring.profiles.active=test'
        }
    }
      stage('Package') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        /* --------------------------
              🌟 SONARQUBE ICI 🌟
           -------------------------- */
      /* stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=YosrDevops \
                          -Dsonar.host.url=http://localhost:9000 \
                          -Dsonar.login=$TOKEN
                    """
                }
            }
        }*/
        stage('Code Quality - SonarQube') {
    steps {
        withSonarQubeEnv('local-sonarqube') {
            sh """
                mvn sonar:sonar \
                  -Dsonar.projectKey=student-management \
                  -Dsonar.projectName=student-management \
                  -Dsonar.host.url=$SONAR_HOST_URL \
                  -Dsonar.token=$SONAR_AUTH_TOKEN
            """
        }
    }
}



       

        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${IMAGE_NAME}:latest
                """
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}",
                    usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {

                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"

                    sh """
                        docker push ${IMAGE_NAME}:${BUILD_NUMBER}
                        docker push ${IMAGE_NAME}:latest
                    """
                }
            }
        }
    }

    post {
        always { echo "Pipeline finished" }
        success { echo "Build succeeded!" }
        failure { echo "Build failed!" }
    }
}
