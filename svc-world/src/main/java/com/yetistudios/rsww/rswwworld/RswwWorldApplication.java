package com.yetistudios.rsww.rswwworld;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@EnableScheduling
public class RswwWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwWorldApplication.class, args);
	}


	@Bean
	public Queue defaultQueue() {
		return new Queue("testqueue", true);
	}

}
