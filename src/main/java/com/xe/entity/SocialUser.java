package com.xe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xe.entity.api.Exchange;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "social_users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SocialUser {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "soc-user_ex",
            joinColumns = {@JoinColumn(name = "us_id", referencedColumnName = "soc-user_id")},
            inverseJoinColumns = {@JoinColumn(name = "ex_id", referencedColumnName = "exchange_id")}
    )
    Collection<Exchange> exchanges;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String regId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soc-user_id")
    private long id;
}
