package com.yetistudios.rsww.rswwworld.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("datasrc")
public class DataSourceAutoConfigConditional extends DataSourceAutoConfiguration {
}
