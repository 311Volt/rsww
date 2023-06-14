#!/bin/bash

function push_img() {
	# $1 - prefixed service name
	docker image tag "$1:latest" "localhost:5000/$1:latest"
	docker push "localhost:5000/$1"
}

push_img rsww_175651-axonserver
push_img rsww_175651-rproxy
push_img rsww_175651-frontend
push_img rsww_175651-svctravelagency
push_img rsww_175651-svcflight
push_img rsww_175651-svctouroperatorcmd
push_img rsww_175651-svcgateway
push_img rsww_175651-svcpayment
push_img rsww_175651-svctouroperatorquery
push_img rsww_175651-svchotel
push_img rsww_175651-svcsagaorchestrator
