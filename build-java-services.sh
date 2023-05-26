#!/bin/bash

mvn package -Dmaven.test.skip=true

function copy_jar() {
	# $1 - service name
	cp "./$1/target/$1-0.0.1-SNAPSHOT.jar" "./docker/ctx-$1/deployments/service.jar"
}

copy_jar "svc-client"
copy_jar "svc-gateway"
copy_jar "svc-hotel"
copy_jar "svc-tour-operator"
copy_jar "svc-travel-agency"
