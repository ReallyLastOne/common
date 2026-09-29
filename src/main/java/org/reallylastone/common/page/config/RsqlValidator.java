package org.reallylastone.common.page.config;

import cz.jirutka.rsql.parser.RSQLParser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RsqlValidator implements ConstraintValidator<Rsql, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            new RSQLParser().parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
