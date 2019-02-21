#!/bin/bash

mvn clean package
txt=$(docker image build .)
line=$(grep -o "Successfully built .*" <<< "$txt")
IFS=' ' read -ra ADDR <<< "$line"

imageId=${ADDR[2]}
docker tag $imageId vroom18/tweet-slacker:latest
docker push vroom18/tweet-slacker:latest
