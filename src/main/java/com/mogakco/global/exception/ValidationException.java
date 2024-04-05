package com.mogakco.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationException {

    private String field;

    private String value;

    private String reason;

    public static List<ValidationException> of(final String field, final String value, final String reason) {
        final List<ValidationException> validationExceptions = new ArrayList<>();
        validationExceptions.add(new ValidationException(field, value, reason));

        return validationExceptions;
    }

    public static List<ValidationException> of(final BindingResult bindingResult) {
        final List<FieldError> validationExceptions = bindingResult.getFieldErrors();

        return validationExceptions.stream().map(error -> new ValidationException(error.getField(),
                error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                error.getDefaultMessage())).toList();
    }
}
