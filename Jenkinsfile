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

        stage('Test') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -Dspring.profiles.active=test'
            }
        }

        /* --------------------------
              🌟 SONARQUBE 🌟
           -------------------------- */
        stage('Code Quality - SonarQube') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    sh '''
                        mvn sonar:sonar \
                          -Dsonar.projectKey=student-management \
                          -Dsonar.projectName=student-management
                    '''
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
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDENTIALS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"

                    sh """
                        docker push ${IMAGE_NAME}:${BUILD_NUMBER}
                        docker push ${IMAGE_NAME}:latest
                    """
                }
            }
        }
    

     stage('Deploy to Kubernetes') {
            steps {
                sh '''
                 KUBECONFIG=/var/lib/jenkins/.kube/config kubectl apply -f k8s/mysql-deployment.yaml -n devops
                 KUBECONFIG=/var/lib/jenkins/.kube/config kubectl apply -f k8s/spring-deployment.yaml -n devops
                 '''
            }
        }
    }


    post {
        always  { echo "Pipeline finished" }
        success { echo "Build succeeded!" }
        failure { echo "Build failed!" }
    }
}
