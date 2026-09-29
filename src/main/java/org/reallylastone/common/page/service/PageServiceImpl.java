package org.reallylastone.common.page.service;

import java.util.function.Consumer;

import org.reallylastone.common.model.ViewEntity;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl implements PageService {

    @Override
    public <T extends ViewEntity> DataPage<T> getPage(String rsql, Pageable pageable,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor) {

        return getPage(pageable, jpaSpecificationExecutor, RSQLJPASupport.toSpecification(rsql),
                null);
    }

    @Override
    public <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor) {

        return getPage(PageRequest.of(request.getPageNumber(), request.getPageSize()),
                jpaSpecificationExecutor, RSQLJPASupport.toSpecification(request.getRsql()), null);
    }

    @Override
    public <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Specification<T> specification) {

        Specification<T> rsqlSpec = RSQLJPASupport.toSpecification(request.getRsql());

        return getPage(PageRequest.of(request.getPageNumber(), request.getPageSize()),
                jpaSpecificationExecutor, rsqlSpec.and(specification), null);
    }

    @Override
    public <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Consumer<T> customizer) {

        return getPage(PageRequest.of(request.getPageNumber(), request.getPageSize()),
                jpaSpecificationExecutor, RSQLJPASupport.toSpecification(request.getRsql()),
                customizer);
    }

    @Override
    public <T extends ViewEntity> DataPage<T> getPage(QueryPageRequest request,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Specification<T> specification,
            Consumer<T> customizer) {

        Specification<T> rsqlSpec = RSQLJPASupport.toSpecification(request.getRsql());

        return getPage(PageRequest.of(request.getPageNumber(), request.getPageSize()),
                jpaSpecificationExecutor, rsqlSpec.and(specification), customizer);
    }

    private static <T extends ViewEntity> DataPage<T> getPage(Pageable pageable,
            JpaSpecificationExecutor<T> jpaSpecificationExecutor, Specification<T> specification,
            Consumer<T> customizer) {

        Page<T> items = jpaSpecificationExecutor.findAll(specification, pageable);

        if (customizer != null) {
            items.getContent().forEach(customizer);
        }

        return new DataPage<>(items);
    }
}
