package org.reallylastone.common.page.query;

import org.reallylastone.common.page.config.Rsql;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class QueryPageRequest {
    @Min(0)
    @NotNull
    private Integer pageNumber = 0;

    @Max(1000)
    @Min(1)
    @NotNull
    private Integer pageSize = 20;

    @Rsql
    private String rsql;
}
