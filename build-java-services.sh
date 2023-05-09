#!/bin/bash

mvn package -Dmaven.test.skip=true

cp ./svc-agency/target/svc-agency-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-agency.jar
cp ./svc-client/target/svc-client-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-client.jar
cp ./svc-gateway/target/svc-gateway-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-gateway.jar
cp ./svc-hotel/target/svc-hotel-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-hotel.jar
cp ./svc-tour-operator/target/svc-tour-operator-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-tour-operator.jar
cp ./svc-travel-agency/target/svc-travel-agency-0.0.1-SNAPSHOT.jar ./dockerfiles/execs_for_docker/svc-travel-agency.jar
