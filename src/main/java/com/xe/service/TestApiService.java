package com.xe.service;

import com.xe.entity.Exchange;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestApiService {

    private final RestTemplate rest;

    public TestApiService(RestTemplate rest) {
        this.rest = rest;
    }

    public Exchange obtain_rates() {
        HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        String url = "https://api.exchangeratesapi.io/latest";
        return rest.exchange(url, HttpMethod.GET, entity, Exchange.class).getBody();
    }
}
