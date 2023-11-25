package com.example.dockerversion.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class GenericExceptionResponse {
    private String errorMessage;
    private Date errorDate;
}
