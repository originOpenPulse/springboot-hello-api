cd ./
mvn deploy
(secure) ant pfc.project-deploy
ant maven.spring-boot
mvn -f pom-pack.xml package
(secure) ant dist-jar

cd ./
ant install-all
step 1: docker-image
step 2: harbor-image
step 3: version-chart
step 4: harbor-chart

cd ../dist
step 5a: k3s-deploy
step 5b: k3s-upgrade
step 6  k3s-undeploy
step 7: k3s-list