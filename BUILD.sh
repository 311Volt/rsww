#!/bin/bash

mvn package -Dmaven.test.skip=true
cp ./svc-touroperator/target/rsww-touroperator-0.0.1-SNAPSHOT.jar ./docker/svc-touroperator/touroperator.jar
cp ./svc-hotel/target/rsww-hotel-0.0.1-SNAPSHOT.jar ./docker/svc-hotel/hotel.jar
