package com.xe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestApiService {

    private final RestTemplate rest;

    public TestApiService(RestTemplate rest) {
        this.rest = rest;
    }

    public String obtain_rates() {
        String url = "https://open.exchangerate-api.com/v6/latest";
        return rest.getForObject(url, String.class);
    }
}
