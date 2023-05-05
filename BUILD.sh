#!/bin/bash

mvn package -Dmaven.test.skip=true
cp ./svc-touroperator/target/rsww-touroperator-0.0.1-SNAPSHOT.jar ./docker/svc-touroperator/touroperator.jar
cp ./svc-world/target/rsww-world-0.0.1-SNAPSHOT.jar ./docker/svc-world/world.jar
