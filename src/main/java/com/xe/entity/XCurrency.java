package com.xe.entity;

import lombok.Data;

@Data
public class XCurrency {
    private final String baseCcy;
    private final String quoteCcy;

    public static enum Type {
        EUR, USD, CAD, HKD, ISK, PHP, DKK, HUF, CZK,
        AUD, RON, SEK, IDR, INR, BRL, RUB, HRK, JPY,
        THB, CHF, SGD, PLN, BGN, TRY, CNY, NOK, NZD,
        ZAR, MXN, ILS, GBP, KRW, MYR
    }
}
