/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    stages {
        stage('Build Backend') {
            steps {
                mvn clean package -DskipTestes=true
            }
        }
    }
}
