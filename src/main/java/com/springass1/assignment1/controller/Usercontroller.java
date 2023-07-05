package com.springass1.assignment1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.model.Address;
import com.springass1.assignment1.model.Coordinates;
import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import com.springass1.assignment1.repositry.Userrepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class Usercontroller {

    @Autowired
    private Userrepositry apis;

    @PostMapping("create")
    public int create(@RequestBody List<EgovUser> user){
        return apis.insert(user);
    }

    @PostConstruct
    public void tabl(){
         apis.createTable();
    }


    @GetMapping("alluser")
        public List<EgovUser> alluser(){
        return apis.findalluser();
    }

    @PostMapping("search")
    public EgovUser search(@RequestBody Searchuser user){
         return apis.finduser(user);
    }

    @DeleteMapping("delete")
    public void delete(@RequestBody EgovUser user){
          apis.delete(user);
    }
    @PutMapping("update")
    public int update(@RequestBody List<EgovUser>user){

         return apis.update(user);
    }



}
