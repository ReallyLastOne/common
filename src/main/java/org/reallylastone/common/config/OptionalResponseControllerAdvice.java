package org.reallylastone.common.config;

import java.util.Optional;

import org.reallylastone.common.exception.ResourceNotFoundException;
import org.reallylastone.common.i18n.Messages;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class OptionalResponseControllerAdvice implements ResponseBodyAdvice {
    private final Messages messages;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getParameterType().equals(Optional.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (returnType.getParameterType().equals(Optional.class)) {
            return ((Optional<?>) body).orElseThrow(() -> new ResourceNotFoundException(
                    messages.getMessage("object.not.found", request.getURI())));
        }
        return body;
    }
}
