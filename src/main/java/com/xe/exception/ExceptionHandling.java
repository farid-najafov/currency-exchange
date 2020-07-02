package com.xe.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionHandling {

    private Date timeStamp;
    private String message;
    private String details;
}
