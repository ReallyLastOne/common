package org.reallylastone.common.page.model;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataPage<T> {
    private final List<T> content;
    private final long totalElements;
    private final int count;
    private final int totalPages;
    private final int pageSize;
    private final int pageNumber;
    private final boolean first;
    private final boolean last;

    public DataPage(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.count = content.size();
        this.pageSize = page.getPageable().getPageSize();
        this.pageNumber = page.getPageable().getPageNumber();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
