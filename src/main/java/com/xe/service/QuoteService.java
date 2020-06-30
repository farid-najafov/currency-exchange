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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class QuoteService {

    private final RestTemplate rest;
    private int a = 0;

    public QuoteService(RestTemplate rest) {
        this.rest = rest;
    }
//
//    public List<Quote> get_rates(String baseCcy) {
//        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
//        QResponse forObject = rest.getForObject(url, QResponse.class);
//
////        List<XCurrency> quote_currencies = new ArrayList<>(forObject.getRates().keySet());
//        ArrayList<String> rates = new ArrayList<>(forObject.getRates().values());
////        XCurrency base_currency = forObject.getBase();
//        Date date = forObject.getDate();
//
////        IntStream.range(0, rates.size()).mapToObj(qc -> new Quote(
////                forObject.getBase(),
////
////                qc,
////                Double.valueOf(rates.get(qc)),
////                date)).collect(Collectors.toList());
////
//
//        AtomicInteger i = new AtomicInteger();
//        List<Quote> quotes = forObject
//                .getRates()
//                .keySet()
//                .stream()
//                .map(qc -> new Quote(
//                        forObject.getBase(),
//                        qc,
//                        Double.valueOf(rates.get(a++)),
//                        date))
//                .collect(Collectors.toList());
//
//        return quotes;
//    }
    public Quote get_rate_for_specific_exchange(String baseCcy,String quoteCcy) {
        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
        QResponse forObject = rest.getForObject(url, QResponse.class);

        Quote quote = forObject.getRates().entrySet().stream().
                filter(a -> a.getKey().name().equals(quoteCcy)).
                map(a -> new Quote(
                        forObject.getBase(),
                        forObject.getRates().entrySet().stream().
                                filter(b -> b.getKey().name().equals(quoteCcy)).
                                map(Map.Entry::getKey).
                                findFirst().orElse(null),
                        Double.valueOf(a.getValue()),
                        forObject.getDate()

                )).findFirst().orElse(null);

        return quote;
    }
}
