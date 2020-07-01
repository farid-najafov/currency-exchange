package com.xe.entity.api;

import com.xe.util.XCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    public XCurrency base_ccy;
    public XCurrency quote_ccy;
    public Double rate;
    public Date date;

    public Quote(String base,String quote) {
        this.base_ccy = XCurrency.valueOf(base);
        this.quote_ccy = XCurrency.valueOf(quote);
    }
}
