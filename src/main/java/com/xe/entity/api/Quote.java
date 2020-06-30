package com.xe.entity.api;

import com.xe.util.XCurrency;
import lombok.Value;

import java.util.Date;

@Value
public class Quote {
    public XCurrency base_ccy;
    public XCurrency quote_ccy;
    public Double rate;
    public Date date;
}
