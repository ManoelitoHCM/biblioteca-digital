package com.biblioteca.biblioteca_digital.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnoNaoFuturoValidator.class)
public @interface AnoNaoFuturo {
    String message() default "Ano de publicação não pode ser no futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


