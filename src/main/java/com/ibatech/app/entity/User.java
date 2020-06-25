package com.ibatech.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibatech.app.validation.FieldMatch;
import com.ibatech.app.validation.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity(name = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @NotNull(message = "Full Name cannot be null")
    @Size(min = 1, message = "is required")
    private String fullName;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "must greater than 8 digts")
    private String password;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "must greater than 8 digts")
    private String matchingPassword;

    @ValidEmail
    @NotNull(message = "Email cannot be null")
    @Size(min = 1, message = "is required")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "us_ex",
            joinColumns = {@JoinColumn(name = "us_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "ex_id", referencedColumnName = "exchange_id")}
    )
    Collection<Exchange> exchanges;

    public User(String name, String password, String matchingPassword, String mail, Collection<Exchange> exchanges) {
        this.fullName = name;
        this.password = password;
        this.email = mail;
        this.matchingPassword = matchingPassword;
        this.exchanges = exchanges;
    }
}
