package com.xe.entity.ext_api;

import com.xe.util.XCurrency;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class QResponse {
    private Map<XCurrency, String> rates;
    private XCurrency base;
    private Date date;

}
