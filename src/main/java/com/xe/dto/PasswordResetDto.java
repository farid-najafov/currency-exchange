package com.xe.dto;

import com.xe.validation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "Password fields must match")
public class PasswordResetDto {

    @NotNull(message = "Password cannot be null")
    @Size(min = 3, message = "must be greater than 3 digits")
    private String password;

    @NotNull(message = "Password cannot be null")
    @Size(min = 1, message = "must be greater than 3 digits")
    private String confirmPassword;
    private String token;
}
