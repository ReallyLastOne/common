package org.reallylastone.common.page.config;

import java.util.Base64;

import org.reallylastone.common.exception.BadRequestException;
import org.reallylastone.common.i18n.Messages;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class QueryPageRequestConverter implements Converter<String, QueryPageRequest> {

    private final ObjectMapper objectMapper;
    private final Messages messages;

    @Override
    public QueryPageRequest convert(String source) {
        try {
            return objectMapper.readValue(Base64.getUrlDecoder().decode(source),
                    QueryPageRequest.class);
        } catch (Exception ignored) {
            try {
                return objectMapper.readValue(source, QueryPageRequest.class);
            } catch (JacksonException e) {
                log.error("Invalid QueryPageRequest", e);
                throw new BadRequestException(messages.getMessage("query.request.invalid"));
            }
        }
    }
}
