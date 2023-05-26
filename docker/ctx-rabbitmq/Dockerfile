FROM rabbitmq:3.11.16-management
RUN apt-get update
RUN apt-get install -y curl
EXPOSE 4369 5671 5672 25672 15671 15672

ENV RABBITMQ_USER testuser
ENV RABBITMQ_PASSWORD testpassword
ENV RABBITMQ_PID_FILE /var/lib/rabbitmq/mnesia/rabbitmq

ADD init.sh /init.sh
RUN chmod +x /init.sh

CMD ["scripts/init.sh"]