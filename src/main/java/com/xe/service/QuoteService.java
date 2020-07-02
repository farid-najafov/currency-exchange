package com.xe.service;

import com.xe.entity.api.Quote;
import com.xe.entity.ext_api.QResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private final RestTemplate rest;

    public QuoteService(RestTemplate rest) {
        this.rest = rest;
    }

    public Quote get_rate_for_specific_exchange(String baseCcy, String quoteCcy) {
        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
        QResponse forObject = rest.getForObject(url, QResponse.class);

        return forObject.getRates().entrySet().stream().
                filter(a1 -> a1.getKey().name().equals(quoteCcy)).
                map(a1 -> new Quote(
                        forObject.getBase(),
                        forObject.getRates().keySet().stream().
                                filter(s -> s.name().equals(quoteCcy)).
                                findFirst().orElse(null),
                        Double.valueOf(a1.getValue()),
                        forObject.getDate()

                )).findFirst().orElse(null);
    }
}
