package com.springass1.assignment1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springass1.assignment1.Kafka.KafkaProducer;
import com.springass1.assignment1.service.Makeuser;
import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import com.springass1.assignment1.repositry.Userrepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class Usercontroller {

    @Autowired
    private Userrepositry repositry;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private Makeuser objectmaker;

    @PostConstruct
    public void table(){
        repositry.CreateTable();
    }

    @PostMapping("create")
    public String create(@RequestBody List<EgovUser> user) throws JsonProcessingException {
        for(EgovUser x: user) {
            if(repositry.CheckUserExist(x)) {
                EgovUser USER = objectmaker.make(x);
                producer.sendmessage("createusertopic", USER);
            }
        }
        return "CREATED";
    }


    @GetMapping("alluser")
    public List<EgovUser> alluser(){
        return repositry.FindAllUser();
    }


    @PostMapping("search")
    public List<EgovUser> search(@RequestBody Searchuser user){
        return repositry.FindUser(user);
    }


    @DeleteMapping("delete")
    public void delete(@RequestBody EgovUser user){
        repositry.Delete(user);
    }


    @PutMapping("update")
    public String update(@RequestBody List<EgovUser>user) throws JsonProcessingException {
        for(EgovUser x: user) {
            if(repositry.UpdateUserExist(x) && repositry.CheckByID(x)) {
                producer.sendmessage("updateusertopic", x);
            }
        }
        return "UPDATED";
    }



}
