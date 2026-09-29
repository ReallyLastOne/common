package org.reallylastone.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JacksonUtils {

    private final ObjectMapper objectMapper;

    public <T> T readValue(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(
                    "Could not parse String " + content + " to class " + clazz.getName(), e);
        }
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Could not serialize Object " + value, e);
        }
    }

    public Map<String, Object> toMap(Object value) {
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(writeValueAsString(value), typeRef);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
