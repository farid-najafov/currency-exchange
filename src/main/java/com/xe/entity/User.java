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

@Entity()
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_id")
     private Long id;

//    @NotNull(message = "Insert your full name")
//    @Size(min = 1, message = "is required")
    @Column(name = "fullname")
    private String fullName;

//    @NotNull(message = "Set up a password")
//    @Size(min = 3, message = "must greater than 3 digits")
    @Column(name = "password")
    private String password;

//    @Email
//    @NotNull(message = "Insert your email")
//    @Size(min = 1, message = "is required")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "us_ex",
            joinColumns = {@JoinColumn(name = "us_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ex_id", referencedColumnName = "exchange_id")}
    )
    private Collection<Exchange> exchanges;

    public User(String name, String password, String mail, Collection<Exchange> exchanges) {
        this.fullName = name;
        this.password = password;
        this.email = mail;
        this.exchanges = exchanges;
    }

}
