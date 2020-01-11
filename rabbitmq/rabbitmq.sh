#!/bin/bash
docker build -t therabbit .
docker run --name therabbit -d -p 15672:15672 -p 5672:5672 therabbit