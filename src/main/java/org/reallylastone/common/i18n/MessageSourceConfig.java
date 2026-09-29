package org.reallylastone.common.i18n;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class MessageSourceConfig {

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    private static final List<Locale> SUPPORTED_LOCALES = List.of(Locale.ENGLISH);

    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(DEFAULT_LOCALE);
        resolver.setSupportedLocales(SUPPORTED_LOCALES);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        var source = new ReloadableResourceBundleMessageSource();
        source.setBasenames("classpath:messages/messages", "classpath:messages/messages-common");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(DEFAULT_LOCALE);
        return source;
    }
}
