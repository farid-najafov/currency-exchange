package com.xe.dto;

import com.xe.entity.Exchange;
import com.xe.validation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Collection;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
@Data
public class UserRegistrationDto {

    @NotNull(message = "Insert your full name")
    @Size(min = 1, message = "is required")
    private String fullName;

    @NotNull(message = "Set up a password")
    @Size(min = 3, message = "must greater than 3 digits")
    private String password;

    @NotNull(message = "Set up a password")
    @Size(min = 3, message = "must greater than 3 digits")
    private String confirmPassword;

    @Email
    @NotNull(message = "Insert your email")
    @Size(min = 1, message = "is required")
    private String email;

    private Collection<Exchange> exchanges;

}
