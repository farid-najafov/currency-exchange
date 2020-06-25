package com.ibatech.project.entity;


import com.ibatech.project.validation.FieldMatch;
import com.ibatech.project.validation.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
}
