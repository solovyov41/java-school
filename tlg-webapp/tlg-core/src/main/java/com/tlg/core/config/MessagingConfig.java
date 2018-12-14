package com.tlg.core.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.jms.ConnectionFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        objectMapper.setDateFormat(df);
        return objectMapper;
    }

    @Bean("artemisTopic")
    public String getArtemisTopic() {
        return artemisTopic;
    }

}
