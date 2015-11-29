package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.QueryDTO;

import java.util.Map;

public class QueryDTOBuilder {
    private Map<String, Map<String, String>> pages;

    public QueryDTOBuilder setPages(Map<String, Map<String, String>> pages) {
        this.pages = pages;
        return this;
    }

    public QueryDTO createQueryDTO() {
        return new QueryDTO(pages);
    }
}