package com.springass1.assignment1.Kafka;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.repositry.Userrepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private Userrepositry repositry;
    @Autowired
    ObjectMapper objectMapper;


    @KafkaListener(topics = "createusertopic" ,groupId = "group")
    public void listentotopic(String x) throws JsonProcessingException {
       try {
           EgovUser egovuser = objectMapper.readValue(x, EgovUser.class);
           repositry.Insert(egovuser);
       }
       catch (Exception e){
           e.printStackTrace();
       }

    }

    @KafkaListener(topics = "upadteusertopic",groupId = "group")
    public void listen(String x) throws JsonProcessingException {
        try {
            EgovUser egovuser = objectMapper.readValue(x, EgovUser.class);
            repositry.Update(egovuser);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



}
