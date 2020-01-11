#!/bin/bash
docker stop loggerserver
docker rm $(docker ps -a -f status=exited -q)
docker build -t loggerserver .
docker run --name loggerserver loggerserver
