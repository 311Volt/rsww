#!/bin/bash

(cd ..; ./BUILD.sh) && docker-compose down && docker-compose up --build