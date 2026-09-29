package org.reallylastone.common.page.config;

import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.page.query.QueryRequest;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QueryRequestArgumentResolver implements HandlerMethodArgumentResolver {

    private final QueryPageRequestConverter converter;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(QueryRequest.class)
                && QueryPageRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var annotation = parameter.getParameterAnnotation(QueryRequest.class);
        var raw = webRequest.getParameter(annotation.name());
        var request =
                raw == null || raw.isBlank() ? new QueryPageRequest() : converter.convert(raw);

        var values = new MutablePropertyValues();
        webRequest.getParameterMap().forEach((name, params) -> {
            if (params.length > 0) {
                values.add(name, params[0]);
            }
        });

        var binder = binderFactory.createBinder(webRequest, request, annotation.name());
        binder.bind(values);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
        }
        return request;
    }
}
