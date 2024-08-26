#!/usr/bin/env sh

imageName='auto-cicd'
imageVersion='v0.0.1'
newOutsidePort='10099'
newContainerPort='10099'


git checkout -b release
git pull origin release

mvn -B clean package -Dmaven.test.skip=true
docker build -f Dockerfile -t $imageName:$imageVersion .
docker tag $imageName:$imageVersion 192.168.30.171:8081/xjtest/$imageName:$imageVersion
docker login -u admin -p connectivity_Harbor.12345 192.168.30.171:8081
docker push 192.168.30.171:8081/xjtest/$imageName:$imageVersion

# The following 4 lines are required to be changed according to circumstance


echo '----------begin delete old container----------'
oldContainerId=`docker ps|grep $imageName|awk '{print $1}'`
echo 'old container id:'$oldContainerId
docker rm -f $oldContainerId
echo '----------finish delete old container----------'
echo ''

echo '----------begin run new container----------'
imageId=`docker images| sed -n 2p|awk '{print $3}'`
echo 'imageId:'$imageId
docker tag $imageId $imageName:$imageVersion
echo $imageName-$imageVersion
docker run -dti --name $imageName-$imageVersion -p$newOutsidePort:$newContainerPort $imageId
echo '----------finsh run new container----------'

