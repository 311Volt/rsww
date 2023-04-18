package com.yetistudios.rsww.rswwtouroperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RswwTouroperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwTouroperatorApplication.class, args);
	}

}
