package com.xe.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class PasswordForgotDto {

    @Email
    @NotEmpty(message = "cannot be empty")
    private String email;

}
