package com.springass1.assignment1.service;

import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Userapis {

    @Autowired
    JdbcTemplate jdbctemplate;

    public EgovUser finduser(Searchuser xyz){
        try {
            int id = xyz.getId();
            String mobilenumber = xyz.getMobilenumber();
            return jdbctemplate.queryForObject("select * from EGOV_USER where id =? and mobilenumber=?", new Object[]{id, mobilenumber},
                    new BeanPropertyRowMapper<EgovUser>(EgovUser.class));
        }
        catch (Exception e){
            System.out.println("error occured in finduser");
            return new EgovUser();
        }

    }

    public int insert(EgovUser x){
            return  jdbctemplate.update("insert into EGOV_USER (id,name,gender,mobilenumber,address)"+ "values(?,?,?,?,?)",
                    new Object[]{x.getId(),x.getName(),x.getGender(),x.getMobilenumber(),x.getAddress()});

    }

    public void delete(EgovUser y){
        jdbctemplate.update("delete from EGOV_USER where id=?",new Object[]{y.getId()});
    }

    public int update(EgovUser x){
        return jdbctemplate.update("update EGOV_USER" + " set name= ?, gender=?,mobilenumber=?,address=?" + "where id = ?" ,
                new Object[]{x.getName(),x.getGender(),x.getMobilenumber(),x.getAddress(),x.getId()});
    }

}
