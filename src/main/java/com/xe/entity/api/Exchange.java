package com.xe.entity.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xe.enums.XCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "base")
    @Enumerated(EnumType.STRING)
    public XCurrency base_ccy;

    @Column(name = "quote")
    @Enumerated(EnumType.STRING)
    public XCurrency quote_ccy;

    @Column(name = "rate")
    public Double rate;

    @Column(name = "amount")
    private double amount;

    @Column(name = "result")
    private double result;

    @Column(name = "date")
    private Date date;

    @Column(name = "localDate")
    private LocalDateTime ld;

    public Exchange(XCurrency base_ccy, XCurrency quote_ccy, Double rate, Date date) {
        this.base_ccy = base_ccy;
        this.quote_ccy = quote_ccy;
        this.rate = rate;
        this.date = date;
        this.ld = LocalDateTime.now();
    }

}
