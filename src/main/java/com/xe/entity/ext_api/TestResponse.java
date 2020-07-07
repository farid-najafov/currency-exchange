package com.xe.entity.ext_api;

import com.xe.enums.XCurrency;
import lombok.Data;

import java.util.*;

@Data
public class TestResponse {
    SortedMap<String, Map<XCurrency,Double>> rates;
    String start_at;
    String base;
    String end_at;
}