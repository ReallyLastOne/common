package org.reallylastone.common.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {
    private static final DateTimeFormatter LOCAL_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public JsonMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.addModules(commonModules());
    }

    public static SimpleModule commonModules() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(LOCAL_DATE_FORMAT));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(LOCAL_DATE_FORMAT));
        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(LOCAL_DATE_TIME_FORMAT));
        module.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(LOCAL_DATE_TIME_FORMAT));
        return module;
    }
}
