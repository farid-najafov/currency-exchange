package com.xe.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "exchanges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "rates",
        "base",
        "date"
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    @JsonIgnore
    private int id;

    @JsonProperty("rates")
    @JoinColumn(name = "rates")
    @OneToOne
    private Rates rates;

     @JsonProperty("rate")
     @Column(name = "rate")
     private String rate;

     @JsonProperty("base")
     @Column(name = "base")
     private String baseCcy;

     @JsonProperty("quote")
     @Column(name = "quote")
     private String quoteCcy;


    @JsonProperty("date")
    @Column(name = "date")
    private Date date;


    public Exchange(String s) {
        this.rate = s;
    }
}
