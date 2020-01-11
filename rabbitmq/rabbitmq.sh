#!/bin/bash
docker stop therabbit
docker rm $(docker ps -a -f status=exited -q)
#docker build -t therabbit .
docker run --name therabbit -d -p 5672:5672 -p 15672:15672 therabbit
