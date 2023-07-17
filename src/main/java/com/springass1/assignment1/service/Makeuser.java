package com.springass1.assignment1.service;

import com.springass1.assignment1.model.Address;
import com.springass1.assignment1.model.Coordinates;
import com.springass1.assignment1.model.EgovUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class Makeuser {

    @Value("${url}")
    private String url;
    public EgovUser make(EgovUser x){

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
        return x;
    }
}
