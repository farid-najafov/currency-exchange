package com.xe.service;

import com.xe.util.XCurrency;
import com.xe.entity.api.Quote;
import com.xe.entity.ext_api.QResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final RestTemplate rest;

    public QuoteService(RestTemplate rest) {
        this.rest = rest;
    }

    public List<Quote> get_rates(String baseCcy) {
        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
        QResponse forObject = rest.getForObject(url, QResponse.class);

//        List<XCurrency> quote_currencies = new ArrayList<>(forObject.getRates().keySet());
        ArrayList<String> rates = new ArrayList<>(forObject.getRates().values());
//        XCurrency base_currency = forObject.getBase();
        Date date = forObject.getDate();
//
//        ArrayList<Quote> quotes = new ArrayList<>();
//        for (int i = 0; i < quote_currencies.size() ; i++) {
//            Quote quote = new Quote(
//                    base_currency,
//                    quote_currencies.get(i),
//                    Double.valueOf(rates.get(i)),
//                    date);
//            quotes.add(quote);
//        }

        AtomicInteger i = new AtomicInteger();
        List<Quote> quotes = forObject
                .getRates()
                .keySet()
                .stream()
                .map(qc -> new Quote(
                        forObject.getBase(),
                        qc,
                        Double.valueOf(rates.get(i.getAndIncrement())),
                        date))
                .collect(Collectors.toList());

        return quotes;
    }
}
