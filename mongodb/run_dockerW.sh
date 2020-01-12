#!/bin/bash
docker stop si-mongo
docker rm si-mongo
mkdir ./data -p
#docker run -d -p 27017:27017 --name si-mongo -v "$(pwd)/data:/data/db" mongo
docker run -d -p 27017:27017 --name si-mongo mongo