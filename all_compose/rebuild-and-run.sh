#!/bin/bash

(cd ..; ./build-java-services.sh) && docker-compose -f all-compose.yaml down && docker-compose -f all-compose.yaml up --build