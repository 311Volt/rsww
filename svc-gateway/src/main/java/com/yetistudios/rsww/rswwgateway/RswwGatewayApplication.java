package com.yetistudios.rsww.rswwgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class RswwGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwGatewayApplication.class, args);
	}

}
