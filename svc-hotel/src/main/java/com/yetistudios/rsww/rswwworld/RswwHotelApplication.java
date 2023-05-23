package com.yetistudios.rsww.rswwworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableScheduling
public class RswwHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwHotelApplication.class, args);
	}


}
