package com.springass1.assignment1.repositry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.Mapper.UserRowMapper;
import com.springass1.assignment1.model.Address;
import com.springass1.assignment1.model.Coordinates;
import com.springass1.assignment1.model.EgovUser;
import com.springass1.assignment1.model.Searchuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class Userrepositry {

    @Autowired
    JdbcTemplate jdbctemplate;
    public void createTable() {
        jdbctemplate.execute(
                "CREATE TABLE IF NOT EXISTS EGOV_USER (id VARCHAR(255), name VARCHAR(255), gender VARCHAR(255), mobilenumber VARCHAR(255), address JSON, active BOOLEAN, createdtime BIGINT,  PRIMARY KEY (id, active)) PARTITION BY LIST (active)");
        jdbctemplate.execute("CREATE TABLE IF NOT EXISTS active_use PARTITION OF EGOV_USER FOR VALUES IN (TRUE)");
        jdbctemplate.execute("CREATE TABLE IF NOT EXISTS inactive_use PARTITION OF EGOV_USER FOR VALUES IN (FALSE)");
    }

    public EgovUser finduser(Searchuser xyz){
        try {
            String id = xyz.getId();
            boolean active = xyz.isActive();
            String mobilenumber = xyz.getMobilenumber();
            return jdbctemplate.queryForObject("select * from EGOV_USER where id =? and mobilenumber=? and active=? ", new Object[]{id, mobilenumber,active},
                    new UserRowMapper());
        }
        catch (Exception e){
            System.out.println("error occured in finduser");
            return new EgovUser();
        }

    }

    public List<EgovUser> findalluser(){
        return jdbctemplate.query("select * from EGOV_USER",
                new UserRowMapper());
    }

    public Boolean check(EgovUser user){
        List<EgovUser>x = jdbctemplate.query("select * from EGOV_USER where name =? and mobilenumber=?  ", new Object[]{user.getName(),user.getMobilenumber()},
                new UserRowMapper());
        if(x.size()>0){
            return false;
        }
        else{
            return true;
        }
    }
    public int insert(List<EgovUser>user){
        int ans =0;
            for(EgovUser x:user ) {
                if(check(x)) {
                    String url = "https://random-data-api.com/api/v2/users?size=1";
                    RestTemplate restTemplate=new RestTemplate();
                    ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
                    Map<String,Object> randomdata = response.getBody();
                    Map<String,Object> addresska = (Map<String, Object>)randomdata.get("address");

                    Address address = new Address();
                    address.setCity((String) addresska.get("city"));
                    address.setStreet_address((String) addresska.get("street_address"));
                    address.setCountry((String) addresska.get("country"));
                    address.setState((String) addresska.get("state"));
                    address.setZip_code((String) addresska.get("zip_code"));
                    address.setStreet_name((String) addresska.get("street_name"));

                    Map<String,Object> coordinate = (Map<String, Object>)addresska.get("coordinates");
                    Coordinates cordi = new Coordinates();
                    cordi.setLat((Double) coordinate.get("lat"));
                    cordi.setLon((Double) coordinate.get("lng"));

                    address.setCoordinates(cordi);
                    x.setAddress(address);

                    UUID id = UUID.randomUUID();
                    String uuid = id.toString();
                    long time = Instant.now().getEpochSecond();
                    x.setId(uuid);
                    x.setCreatedtime(time);
                    String addressJson;
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        addressJson = objectMapper.writeValueAsString(x.getAddress());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error", e);
                    }

                    jdbctemplate.update("insert into EGOV_USER (id,name,gender,mobilenumber,address,active,createdtime)" + "values(?,?,?,?,?::json,?,?)",
                            new Object[]{x.getId(), x.getName(), x.getGender(), x.getMobilenumber(), addressJson, x.isActive(), x.getCreatedtime()});
                    ans++;
                }
            }
      return ans;
    }

    public void delete(EgovUser y){
        jdbctemplate.update("delete from EGOV_USER where id=?",new Object[]{y.getId()});
    }

    public int update(List<EgovUser>user){
        int ans =0;
        for(EgovUser x:user) {
            String addressJson;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                addressJson = objectMapper.writeValueAsString(x.getAddress());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error", e);
            }
            jdbctemplate.update("update EGOV_USER" + " set name= ?, gender=?,mobilenumber=?,address=?,createdtime=?" + "where id = ?",
                    new Object[]{x.getName(), x.getGender(), x.getMobilenumber(), addressJson,x.getCreatedtime(), x.getId()});
            jdbctemplate.update("update EGOV_USER" + " set name= ?, gender=?,mobilenumber=?,address=?,createdtime=?" + "where id = ?",
                    new Object[]{x.getName(), x.getGender(), x.getMobilenumber(), x.getAddress(),x.getCreatedtime(), x.getId()});

            ans++;
        }
        return ans;
    }

}
