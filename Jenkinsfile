pipeline {
    agent any
    environment {
        GITHUB_CREDS = credentials('GITHUB_CRED')
        CODECOV_TOKEN = credentials('IDENTITY-API_CODECOV_TOKEN')
    }
    options {
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }
    tools {
        jdk 'JDK 10'
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo $JAVA_HOME'
                sh 'mvn -B clean install'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                          execPattern: '**/target/*.exec',
                          classPattern: '**/target/classes',
                          sourcePattern: '**/src/main/java',
                          exclusionPattern: '**/src/test*,**/*Exception*,**/*Config*'
                    )

                }
            }
        }
        stage('Reports') {
            steps {
                sh 'curl -s https://codecov.io/bash | bash -s -- -t $CODECOV_TOKEN'
            }
        }
        stage('Docker Build') {
          steps {
            sh 'docker build -t fundrequestio/identityapi:${BRANCH_NAME} identity-api'
          }
        }
        stage('Docker Push') {
          steps {
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
              sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
              sh "docker push fundrequestio/identityapi:${BRANCH_NAME} && echo 'pushed'"
            }
          }
        }
    }
}
