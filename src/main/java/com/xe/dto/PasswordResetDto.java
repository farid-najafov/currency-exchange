package com.xe.dto;

import com.xe.validation.FieldMatch;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetDto {

    private String password;
    private String confirmPassword;
    private String token;
}
