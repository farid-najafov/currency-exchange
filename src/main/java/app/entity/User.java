package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int id;

    @Column(name = "user_name")
    String name;

    @Column(name = "user_password")
    String password;

    @Column(name = "user_mail")
    String mail;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "us_ex",
            joinColumns = {@JoinColumn(name = "us_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "ex_id", referencedColumnName = "exchange_id")}
    )
    Collection<Exchange> exchanges;

    public User(String name, String password, String mail, Collection<Exchange> exchanges) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.exchanges = exchanges;
    }
}
