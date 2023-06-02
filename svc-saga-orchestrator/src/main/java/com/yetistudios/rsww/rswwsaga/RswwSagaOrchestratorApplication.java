package com.yetistudios.rsww.rswwsaga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class RswwSagaOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwSagaOrchestratorApplication.class, args);
	}

}
