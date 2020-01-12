#!/bin/bash
docker stop flightservice
docker rm $(docker ps -a -f status=exited -q)
docker build -t flightservice .
docker run --name flightservice -p 6565:6565 flightservice
