package org.reallylastone.common.page.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RsqlValidator.class)
public @interface Rsql {
    String message() default "{rsql.incorrect}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
