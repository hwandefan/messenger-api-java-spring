pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'ls'
                echo "Hello"
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
