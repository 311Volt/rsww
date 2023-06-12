#!/bin/bash

function push_img() {
	# $1 - prefixed service name
	docker image tag "$1:latest" "localhost:5000/$1:latest"
	docker push "localhost:5000/$1"
}

push_img rsww_175651-broker_axon
push_img rsww_175651-frontend
push_img rsww_175651-svc_travel_agency
push_img rsww_175651-svc_flight
push_img rsww_175651-svc_tour_operator_cmd
push_img rsww_175651-svc_gateway
push_img rsww_175651-svc_payment
push_img rsww_175651-svc_tour_operator_query
push_img rsww_175651-svc_hotel
push_img rsww_175651-svc_saga_orchestrator