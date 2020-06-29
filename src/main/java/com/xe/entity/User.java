package com.xe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xe.validation.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity(name = "users")
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @NotNull(message = "Insert your full name")
    @Size(min = 1, message = "is required")
    @Column(name = "fullname")
    private String fullName;

    @NotNull(message = "Set up a password")
    @Size(min = 3, message = "must greater than 3 digits")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Set up a password")
    @Size(min = 3, message = "must greater than 3 digits")
    @Transient
    private String matchingPassword;

    @Email
    @NotNull(message = "Insert your email")
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
