package com.tlg.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private String artemisTopic;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(ChangesType changesType, String json) {
        try (JMSContext context = connectionFactory.createContext()) {
            Topic topic = context.createTopic(artemisTopic);

            Message message = context.createTextMessage(json);
            message.setStringProperty(ChangesType.class.getSimpleName(), changesType.name());

            context.createProducer().send(topic, message);
        } catch (JMSException ex) {
            logger.warn("Could not send message", ex);
        } catch (JMSRuntimeException ex){
            logger.error("Can not send message", ex);
        }
    }

    public void sendMessage(ChangesType changesType, Object o) {
        try {
            String json = objectMapper.writeValueAsString(o);
            sendMessage(changesType, json);
        } catch (JsonProcessingException ex) {
            logger.warn("Could not convert object into json string", ex);
        }
    }
}

