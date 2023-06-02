package com.yetistudios.rsww.rswwgateway.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XStreamConfiguration {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{
                "com.yetistudios.**",
                "org.axonframework.**"
        });
        return xStream;
    }

}
