package com.springass1.assignment1.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.model.EgovUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;
     public void sendmessage(String topic,EgovUser x) throws JsonProcessingException {
         String json = objectMapper.writeValueAsString(x);
        kafkaTemplate.send(topic,json);
     }

}
