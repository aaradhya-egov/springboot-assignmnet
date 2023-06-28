package com.springass1.assignment1.controller;

import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import com.springass1.assignment1.service.Userapis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Usercontroller {

    @Autowired
    private Userapis apis;
    @PostMapping("create")
    public int create(@RequestBody EgovUser y){
        return apis.insert(y);
    }


    @PostMapping("search")
    public EgovUser search(@RequestBody Searchuser o){
         return apis.finduser(o);
    }

    @DeleteMapping("delete")
    public void delete(@RequestBody EgovUser u){
          apis.delete(u);
    }
    @PutMapping("update")
    public int update(@RequestBody EgovUser u){
         return apis.update(u);
    }



}
