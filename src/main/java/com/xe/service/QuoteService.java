package com.xe.service;

import com.xe.entity.ext_api.QResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private final RestTemplate rest;

    public QuoteService(RestTemplate rest) {
        this.rest = rest;
    }

    public QResponse get_rates(String baseCcy) {
        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
  //      String url = "https://api.exchangeratesapi.io/latest?base=USD";
        return rest.getForObject(url, QResponse.class);
    }
}
