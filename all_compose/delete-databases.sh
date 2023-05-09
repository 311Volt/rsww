#!/bin/bash

docker-compose -f ./all-compose.yaml rm -f -v
docker-compose -f ./all-compose.yaml down -v