package com.greenblat.rest.util;

import org.springframework.validation.BindingResult;

import java.util.List;

public class FieldError {
    public static void incorrectFields(BindingResult bindingResult) {
        StringBuilder error = new StringBuilder();

        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.append(fieldError.getField())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }

        throw new MeasurementNotCreatedException(error.toString());
    }
}
