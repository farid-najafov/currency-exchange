package com.xe.entity;


import com.xe.util.XCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exchange2 {
    XCurrency baseCcy;
    XCurrency quoteCcy;
    int basevalue;
    double quoteValue;
    double baseRate;
    double quoteRate;
}
