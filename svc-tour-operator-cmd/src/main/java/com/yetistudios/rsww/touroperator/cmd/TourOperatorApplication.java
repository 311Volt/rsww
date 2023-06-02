package com.yetistudios.rsww.touroperator.cmd;

import com.yetistudios.rsww.touroperator.cmd.command.exception.OfferServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourOperatorApplication.class, args);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer){
		configurer.registerListenerInvocationErrorHandler(
				"offer",
				configuration -> new OfferServiceEventsErrorHandler()
		);
	}
}
