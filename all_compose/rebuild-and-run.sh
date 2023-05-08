#!/bin/bash

(cd ..; cd java-service; ./BUILD.sh) && docker-compose -f all-compose.yaml down && docker-compose -f all-compose.yaml up --build