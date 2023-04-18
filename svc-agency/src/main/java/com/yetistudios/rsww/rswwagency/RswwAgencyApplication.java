package com.yetistudios.rsww.rswwagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RswwAgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwAgencyApplication.class, args);
	}

}
