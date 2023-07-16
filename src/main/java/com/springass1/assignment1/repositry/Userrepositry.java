package com.springass1.assignment1.repositry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.service.UserRowMapper;
import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Userrepositry {
    @Autowired
    JdbcTemplate jdbctemplate;
    public void CreateTable() {
        jdbctemplate.execute(
                "CREATE TABLE IF NOT EXISTS EGOV_USER (id VARCHAR(255), name VARCHAR(255), gender VARCHAR(255), mobilenumber VARCHAR(255), address JSON, active BOOLEAN, createdtime BIGINT,  PRIMARY KEY (id, active)) PARTITION BY LIST (active)");
        jdbctemplate.execute("CREATE TABLE IF NOT EXISTS active_use PARTITION OF EGOV_USER FOR VALUES IN (TRUE)");
        jdbctemplate.execute("CREATE TABLE IF NOT EXISTS inactive_use PARTITION OF EGOV_USER FOR VALUES IN (FALSE)");
    }

    public List<EgovUser> FindAllUser(){
        return jdbctemplate.query("select * from EGOV_USER",
                new UserRowMapper());
    }
    public void Insert(EgovUser user){
        String addressJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            addressJson = objectMapper.writeValueAsString(user.getAddress());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error", e);
        }

        jdbctemplate.update("insert into EGOV_USER (id,name,gender,mobilenumber,address,active,createdtime)" + "values(?,?,?,?,?::json,?,?)",
                user.getId(), user.getName(), user.getGender(), user.getMobilenumber(), addressJson, user.isActive(), user.getCreatedtime());

    }


    public List<EgovUser> FindUser(Searchuser searchuser){
        String sql = "SELECT * FROM EGOV_USER";
        List<Object> arr = new ArrayList<>();
        boolean check = false;

        if (searchuser.getId() != null) {
            sql += " WHERE id = ?";
            arr.add(searchuser.getId());
            check= true;
        }

        if (searchuser.getMobilenumber() != null) {
            if (check) {
                sql += " AND mobileNumber = ?";
            } else {
                sql += " WHERE mobileNumber = ?";
                check = true;
            }
            arr.add(searchuser.getMobilenumber());
        }

        if (searchuser.isActive()) {
            if (check) {
                sql += " AND active = ?";
            } else {
                sql += " WHERE active = ?";
                check = true;
            }
            arr.add(searchuser.isActive());
        }

        return jdbctemplate.query(sql, arr.toArray(), new UserRowMapper());
//


    }

    public void Update(EgovUser user){
            String addressJson;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                addressJson = objectMapper.writeValueAsString(user.getAddress());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error", e);
            }
            jdbctemplate.update("update EGOV_USER" + " set name= ?, gender=?,mobilenumber=?,address=?,createdtime=?" + "where id = ?",
                    user.getName(), user.getGender(), user.getMobilenumber(), addressJson,user.getCreatedtime(), user.getId());

    }

    public void Delete(EgovUser user){
        jdbctemplate.update("delete from EGOV_USER where id=?", user.getId());
    }

    public Boolean CheckByID(EgovUser user){
        int ans = jdbctemplate.queryForObject("SELECT COUNT(*) FROM EGOV_USER WHERE ID = ?" ,Integer.class , user.getId());
        return  ans>0;
    }

    public Boolean CheckUserExist(EgovUser user){
        List<EgovUser>x = jdbctemplate.query("select * from EGOV_USER where name =? and mobilenumber=?  ", new Object[]{user.getName(),user.getMobilenumber()},
                new UserRowMapper());
        return x.size() == 0;
    }

    public Boolean UpdateUserExist(EgovUser user){
        List<EgovUser>x = jdbctemplate.query("select * from EGOV_USER where name =? and mobilenumber=? and id <>? ", new Object[]{user.getName(),user.getMobilenumber(),user.getId()},
                new UserRowMapper());
        return x.size() == 0;
    }

}
