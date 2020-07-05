package com.xe.entity.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xe.enums.XCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "exchanges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    @JsonIgnore
    private int id;

    @JsonProperty("base")
    @Column(name = "base")
    @Enumerated(EnumType.STRING)
    public XCurrency base_ccy;

    @JsonProperty("quote")
    @Column(name = "quote")
    @Enumerated(EnumType.STRING)
    public XCurrency quote_ccy;

    @JsonProperty("rate")
    @Column(name = "rate")
    public Double rate;

    @JsonProperty("amount")
    @Column(name = "amount")
    private double amount;

    @JsonProperty("result")
    @Column(name = "result")
    private double result;

    @JsonProperty("date")
    @Column(name = "date")
    private Date date;

    @JsonProperty("localDate")
    @Column(name = "localDate")
    private LocalDate localDate ;


    public Exchange(XCurrency base_ccy, XCurrency quote_ccy, Double rate, Date date) {
        this.base_ccy = base_ccy;
        this.quote_ccy = quote_ccy;
        this.rate = rate;
        this.date = date;
        this.localDate  = LocalDate.now();
    }


    public Exchange(double s) {
        this.localDate  = LocalDate.now();
        this.rate = s;
        }
}
