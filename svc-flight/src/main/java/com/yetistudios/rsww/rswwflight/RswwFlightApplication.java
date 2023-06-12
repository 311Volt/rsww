package com.yetistudios.rsww.rswwflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class RswwFlightApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwFlightApplication.class, args);
	}

}
