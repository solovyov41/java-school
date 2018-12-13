package com.tlg.core.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.jms.ConnectionFactory;

@Configuration
@PropertySource("classpath:core.properties")
public class MessagingConfig {

    @Value("${artemis.url}")
    private String artemisUrl;

    @Value("${artemis.topic}")
    private String artemisTopic;

    @Value("${artemis.user}")
    private String artemisUser;

    @Value("${artemis.password}")
    private String artemisPassword;

    @Bean
    public ConnectionFactory getConnectionFactory() {
        return new ActiveMQJMSConnectionFactory(artemisUrl, artemisUser, artemisPassword);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean("artemisTopic")
    public String getArtemisTopic() {
        return artemisTopic;
    }

}
