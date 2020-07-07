package com.xe.entity.api;

import com.xe.enums.XCurrency;

import java.util.Date;

public class RateByPeriod {
    XCurrency base;
    XCurrency quote;
    Date start_date;
    Date end_date;
    Double rate;
    Date current_day;
}
