package com.xe.service;

import com.xe.entity.api.Exchange;
import com.xe.entity.ext_api.TestResponse;
import com.xe.entity.ext_api.QResponse;
import com.xe.enums.XCurrency;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Log4j2
public class ExchangeService {

    private final RestTemplate rest;

    public ExchangeService(RestTemplate rest) {
        this.rest = rest;
    }

    public Exchange get_rate_for_specific_exchange(String baseCcy, String quoteCcy) {
        String url = String.format("https://api.exchangeratesapi.io/latest?base=%s", baseCcy);
        QResponse forObject = rest.getForObject(url, QResponse.class);

        return forObject.getRates().entrySet().stream().
                filter(a1 -> a1.getKey().name().equals(quoteCcy)).
                map(a1 -> new Exchange(
                        forObject.getBase(),
                        forObject.getRates().keySet().stream().
                                filter(s -> s.name().equals(quoteCcy)).
                                findFirst().orElse(null),
                        Double.parseDouble(a1.getValue()),
                        forObject.getDate()

                )).findFirst().orElse(null);
    }

    public Exchange get_rate_for_specific_date(String date, String baseCcy, String quoteCcy) throws ParseException {

        Date format = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(date);

        String fmt = new SimpleDateFormat("yyyy-MM-d").format(format);

        String url = String.format("https://api.exchangeratesapi.io/%s?base=%s&symbols=%s", fmt, baseCcy, quoteCcy);

        QResponse obj = rest.getForObject(url, QResponse.class);

        return new Exchange(obj.getBase(),
                obj.getRates().keySet().stream().findFirst().orElse(XCurrency.EUR),
                Double.parseDouble(obj.getRates().get(XCurrency.valueOf(quoteCcy))),
                obj.getDate());
    }

    public TestResponse get_rate_for_specific_interval(String starDate, String endDate, String baseCcy, String quoteCcy) throws ParseException {

//        Date format = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(starDate);
//        String firstDate = new SimpleDateFormat("yyyy-MM-d").format(format);
//
//        Date format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(endDate);
//        String secondDate = new SimpleDateFormat("yyyy-MM-d").format(format2);

        String url = String.format("https://api.exchangeratesapi.io/history?start_at=%s&end_at=%s&base=%s&symbols=%s", starDate, endDate, baseCcy, quoteCcy);

        TestResponse obj = rest.getForObject(url, TestResponse.class);
        log.info(obj);
        return obj;
    }

}