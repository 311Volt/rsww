package com.yetistudios.rsww.rswwtouroperator;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class RswwTouroperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwTouroperatorApplication.class, args);
	}

	@Bean
	public Queue defaultQueue() {
		return new Queue("testqueue", true);
	}

	@RabbitListener(queues = "testqueue")
	public void listen(String msg) {
		System.out.println("message read from [world]: " + msg);
	}

}
