package org.reallylastone.common.page.service;

import java.util.function.Consumer;

import org.reallylastone.common.model.ViewEntity;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageService {

    <T extends ViewEntity> DataPage<T> getPage(String rsql, Pageable pageable,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor);

    <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor);

    <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Specification<T> specification);

    <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Consumer<T> customizer);

    <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Specification<T> specification,
            Consumer<T> customizer);
}
