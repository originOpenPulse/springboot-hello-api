#!/bin/bash
source build.properties

export DEPLOY_HOME=$1
export PROJCT_NAME=$2

case $3 in
#docker-open17
'docker-open17')
  docker pull centos:7
  cd ${DEPLOY_HOME}/docker/jdk-open17/
  ls -lrt .
  #mkdir ./dist
  #cp ${DEPLOY_HOME}/dist/openjdk-17.0.2_linux-x64_bin.tar.gz ./dist/
  docker build -t java:jdk-open17 .
  docker image ls
  ;;

#version-chart
'version-chart')
  cd ${DEPLOY_HOME}/chart/${PROJCT_NAME}
  sed -i 's/chartversion/'"${helm_versn}"'/g' Chart.yaml
  sed -i 's/applicationversion/'"${helm_versn}"'/g' Chart.yaml
  echo 'Chart.yaml'
  cat Chart.yaml
  sed -i 's/imageversion/'"${helm_versn}"'/g' values.yaml
  echo 'values.yaml'
  cat values.yaml
  ;;

#docker-image
'docker-image')
  cd ${DEPLOY_HOME}/docker/${PROJCT_NAME}
  ls -lrt .
  docker build -t ${PROJCT_NAME}:${helm_versn} .
  docker tag ${PROJCT_NAME}:${helm_versn} ${harbor_serv}/${harbor_proj}/${PROJCT_NAME}:${helm_versn}
  docker image ls
  docker image ls|grep none
  ;;

#harbor-image
'harbor-image')
  docker login -u ${harbor_user} -p ${harbor_pass} ${harbor_serv}
  docker push ${harbor_serv}/${harbor_proj}/${PROJCT_NAME}:${helm_versn}
  ;;

#harbor-chart
'harbor-chart')
  cd ${DEPLOY_HOME}/chart
  ls -lrt .
  helm package ${PROJCT_NAME}
  echo "helm install ${PROJCT_NAME} ${DEPLOY_HOME}/${PROJCT_NAME}/<chart.tgz> -n ${k3s_namespace} --dry-run --debug"
  helm cm-push ${PROJCT_NAME} ${helm_repos} --username ${harbor_user} --password ${harbor_pass}
  ;;

#k3s-deploy
'k3s-deploy')
  cd ${DEPLOY_HOME}/chart/${PROJCT_NAME}/
  ls -lrt .
  
  #helm install  
  kubectl get secret regcred -n ${k3s_namespace}
  helm install ${PROJCT_NAME} -n ${k3s_namespace} --version ${helm_versn} ${helm_repos}/${PROJCT_NAME}
  #helm install springboot-hello-api -n default --replace --version 1.0.0 originopenpulse/springboot-hello-api
  #helm upgrade ${PROJCT_NAME} -n ${k3s_namespace} --version ${helm_versn} ${helm_repos}/${PROJCT_NAME}
  kubectl get pods -n ${k3s_namespace}
  
  #help info
  echo "kubectl get pod -n ${k3s_namespace} -o wide"
  echo "kubectl describe pod <pod-name> -n ${k3s_namespace}"
  echo "kubectl logs <pod-name> -n ${k3s_namespace}"
  ;;

#k3s-upgrade
'k3s-upgrade')
  cd ${DEPLOY_HOME}/chart/${PROJCT_NAME}/
  ls -lrt .
  
  #helm install  
  kubectl get secret regcred -n ${k3s_namespace}
  helm upgrade ${PROJCT_NAME} -n ${k3s_namespace} --version ${helm_versn} ${helm_repos}/${PROJCT_NAME}
  #helm install springboot-hello-api -n default --replace --version 1.0.0 originopenpulse/springboot-hello-api
  #helm upgrade ${PROJCT_NAME} -n ${k3s_namespace} --version ${helm_versn} ${helm_repos}/${PROJCT_NAME}
  kubectl get pods -n ${k3s_namespace}
  
  #help info
  echo "kubectl get pod -n ${k3s_namespace} -o wide"
  echo "kubectl describe pod <pod-name> -n ${k3s_namespace}"
  echo "kubectl logs <pod-name> -n ${k3s_namespace}"
  ;;


#k3s-undeploy
'k3s-undeploy')
  cd ${DEPLOY_HOME}
  #helm uninstall
  helm uninstall -n ${k3s_namespace} ${PROJCT_NAME}
  kubectl get pod -n ${k3s_namespace} -o wide
  
  #kubectl delete
  kubectl delete deployment -n ${k3s_namespace} ${PROJCT_NAME}
  kubectl get pod -n ${k3s_namespace} -o wide
  ;;

#k3s-list
'k3s-list')
  helm list -n ${k3s_namespace}
  kubectl get pods -n ${k3s_namespace}
  ;;

*)
    echo "usage: <home> <project> <version-all|docker-image|harbor-image|harbor-chart|k3s-deploy|k3s-undeploy|k3s-list>"
	echo "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
	echo "step 0: version-chart"
	echo "step 1: docker-image"
	echo "step 2: harbor-image"
	echo "step 3: harbor-chart"
	echo "step 4a: k3s-deploy"
	echo "step 4b: k3s-upgrade"
	echo "step 5  k3s-undeploy"
	echo "step 6: k3s-list"
    ;;
esac
