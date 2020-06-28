package com.xe.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "exchanges")
@Data
@NoArgsConstructor

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

    @JsonProperty("base")
    @Column(name = "base")
    private String base;

    @JsonProperty("date")
    @Column(name = "date")
    private Date date;

    public Exchange(String base) {
        this.base = base;
    }

    //    @ManyToOne
//    private User user;

}
