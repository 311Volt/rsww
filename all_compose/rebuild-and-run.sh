#!/bin/bash

(cd ..; ./build-java-services.sh) && docker-compose down && docker-compose up --build