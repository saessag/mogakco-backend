package com.mogakco.global.exception;

import com.mogakco.global.response.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    ErrorResponse errorResponse;
}
