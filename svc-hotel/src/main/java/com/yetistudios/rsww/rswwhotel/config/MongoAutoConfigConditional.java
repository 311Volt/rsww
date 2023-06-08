package com.yetistudios.rsww.rswwhotel.config;

import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class MongoAutoConfigConditional extends MongoAutoConfiguration {
}
