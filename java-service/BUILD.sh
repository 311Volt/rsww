#!/bin/bash

mvn package -Dmaven.test.skip=true

cp ./svc-agency/target/rsww-agency-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-agency.jar
cp ./svc-client/target/rsww-client-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-client.jar
cp ./svc-gateway/target/rsww-gateway-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-gateway.jar
cp ./svc-hotel/target/rsww-hotel-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-hotel.jar
cp ./svc-tourOperator/target/rsww-tourOperator-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-tourOpeartor.jar
cp ./svc-travelAgency/target/svc-travelAgency-0.0.1-SNAPSHOT.jar ../dockerfiles/execs_for_docker/svc-travelAgency.jar
