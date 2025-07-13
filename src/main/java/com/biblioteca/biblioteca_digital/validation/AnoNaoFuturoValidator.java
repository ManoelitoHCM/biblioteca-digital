package com.biblioteca.biblioteca_digital.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class AnoNaoFuturoValidator implements ConstraintValidator<AnoNaoFuturo, Integer> {

    @Override
    public boolean isValid(Integer ano, ConstraintValidatorContext context) {
        return ano == null || ano <= Year.now().getValue();
    }
}

