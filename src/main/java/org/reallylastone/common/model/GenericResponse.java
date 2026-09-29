package org.reallylastone.common.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String WARNING = "warning";

    private String status;
    private String message;
    private T data;
    private Map<String, Object> params;

    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS.equals(status);
    }

    @JsonIgnore
    public boolean isError() {
        return ERROR.equals(status);
    }

    @JsonIgnore
    public boolean isWarning() {
        return WARNING.equals(status);
    }

    public GenericResponse<T> withParam(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);

        return this;
    }

    public static <T> GenericResponse<T> success(String message, T data) {
        return new GenericResponse<>(SUCCESS, message, data, null);
    }

    public static GenericResponse<Void> success(String message) {
        return new GenericResponse<>(SUCCESS, message, null, null);
    }

    public static <T> GenericResponse<T> success() {
        return new GenericResponse<>(SUCCESS, null, null, null);
    }

    public static <T> GenericResponse<T> error(String message, T data) {
        return new GenericResponse<>(ERROR, message, data, null);
    }

    public static GenericResponse<Void> error(String message) {
        return new GenericResponse<>(ERROR, message, null, null);
    }

    public static <T> GenericResponse<T> warning(String message, T data) {
        return new GenericResponse<>(WARNING, message, data, null);
    }

    public static GenericResponse<Void> warning(String message) {
        return new GenericResponse<>(WARNING, message, null, null);
    }

    public static GenericResponse<Void> successWithId(String message, Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return new GenericResponse<>(SUCCESS, message, null, data);
    }
}
