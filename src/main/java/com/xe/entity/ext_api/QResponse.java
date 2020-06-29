package com.xe.entity.ext_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class QResponse {
    private QRates rates;
    private String base;
    private Date date;

}
