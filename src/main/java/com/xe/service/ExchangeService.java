package com.xe.service;


import com.xe.entity.Exchange;
import com.xe.entity.Exchange2;
import com.xe.entity.api.Quote;
import com.xe.util.XCurrency;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final QuoteService quoteService;


    public ExchangeService(QuoteService quoteService) {
        this.quoteService = quoteService;
    }


    public Exchange2 exchange_value(String baseCcy, String quoteCcy, int value) {

        Quote quote = quoteService.get_rate_for_specific_exchange(baseCcy, quoteCcy);

        Double quoteCcy_value = find_quoteCcy_value(value, quote.rate);
        Double quoteCcy_rate = find_quoteCcy_rate(quote.rate);
        return new Exchange2(XCurrency.valueOf(baseCcy),XCurrency.valueOf(quoteCcy),value,quoteCcy_value,quote.rate,quoteCcy_rate);
    }

    public Double find_quoteCcy_value(double value, Double rate) {
        return value * rate;
    }

    public Double find_quoteCcy_rate( Double rate) {
        return 1/rate;
    }

}
