package com.xe.service;

import com.xe.entity.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




@Service
public class TestApiService {

    private final RestTemplate rest;

    public TestApiService(RestTemplate rest) {
        this.rest = rest;
    }

    public Exchange obtain_rates() {

        String url = "https://api.exchangeratesapi.io/latest";
        return rest.getForObject(url,  Exchange.class);
    }
}
