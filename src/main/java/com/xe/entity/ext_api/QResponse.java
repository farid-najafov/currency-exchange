package com.xe.entity.ext_api;

import com.xe.enums.XCurrency;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class QResponse {

    private Map<XCurrency, String> rates;
    private XCurrency base;
    private Date date;

}
