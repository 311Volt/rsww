package com.yetistudios.rsww.rswwmsgbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RswwMsgbusApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswwMsgbusApplication.class, args);
	}

}
