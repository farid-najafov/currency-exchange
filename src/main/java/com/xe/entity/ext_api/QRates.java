package com.xe.entity.ext_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xe.entity.XCurrency;
import lombok.Data;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Set;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class QRates {
//    private List<XCurrency.Type> rates = Arrays.asList(XCurrency.Type.values());
//        Set<Currency> currencies =  Currency.getAvailableCurrencies();
    @JsonProperty("CAD")
    private String CAD;

}
