#!/bin/bash

_term() {
  echo "SIGTERM caught, stopping RabbitMQ"
  kill -TERM "$rmqpid" 2>/dev/null
}

trap _term SIGTERM

( rabbitmqctl wait --timeout 60 "$RABBITMQ_PID_FILE" ; \
rabbitmqctl add_user "$RABBITMQ_USER" "$RABBITMQ_PASSWORD" 2>/dev/null ; \
rabbitmqctl set_user_tags "$RABBITMQ_USER" administrator ; \
rabbitmqctl set_permissions -p / "$RABBITMQ_USER"  ".*" ".*" ".*" ; \
echo "*** User '$RABBITMQ_USER' with password '$RABBITMQ_PASSWORD' completed. ***" ; \
echo "*** Log in the WebUI at port 15672 (example: http://localhost:15672) ***") &

# shellcheck disable=SC2068
rabbitmq-server $@ &
rmqpid=$!
wait "$rmqpid"