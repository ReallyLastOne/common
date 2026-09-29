package org.reallylastone.common.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Messages {
    private final MessageSource messageSource;

    public String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    public String getMessage(String code, Locale locale, Object... args) {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException ignored) {
            log.warn("No message for code {} found", code);
            return code;
        }
    }
}
