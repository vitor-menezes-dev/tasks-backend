/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    stages {
        stage('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            environment {
                sonarqubeScannerHome = tool name: 'SONAR_SCANNER', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${env. sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack  -Dsonar.host.url=${SONAR_HOST_URL}  -Dsonar.login=e7d7a7c4854df7979348658fde4e3d44e34a2de9 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                sleep(5)
                timeout(time:1, unit:'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

