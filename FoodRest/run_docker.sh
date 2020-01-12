#!/bin/bash
docker stop foodservice
docker rm foodservice
docker build -t foodservice .
docker run -p 5009:5009 --name foodservice foodservice