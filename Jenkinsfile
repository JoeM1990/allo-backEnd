pipeline{

  agent any

  tools {
    maven 'maven'
  }

  environment{
    IMAGE_NAME="allolecole-backend"
    IMAGE_TAG="1"
  }

  stages{
    stage("Checkout SCM"){
      steps{
        git branch: 'master', credentialsId: 'GITHUB_CRED', url: 'https://github.com/creasmit/allolecoleBack.git'
      }
    }
    stage("Build app"){
      steps{
        sh "mvn clean"
        sh "mvn install"
      }
    }
    stage("Build docker image"){
      steps{
        script{
          try{
            sh "docker image rm ${IMAGE_NAME}:${IMAGE_TAG} --force"
          }catch(Exception e){
          }
        }
        sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
      }
    }

    stage("Deploy"){
      steps{
        script{
          try{
            sh "docker stop ${IMAGE_NAME}"
          }catch(Exception e){
            echo "No container were running"
          }
          try{
            sh "docker rm ${IMAGE_NAME}"
          }catch(Exception e){
            echo "No container had built before"
          }
        }
        sh "docker run -d -p 8003:8080 --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
      }
    }
  }
}
