package com.xe.entity.api;

import com.xe.enums.XCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateByPeriod {
    XCurrency base;
    XCurrency quote;
    String start_date;
    String end_date;
    Double rate;
    String current_day;

}
