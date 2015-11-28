package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDTO {

    private Map<String,Map<String,String>> pages;

    public Map<String, Map<String, String>> getPages() {
        return pages;
    }

    public void setPages(Map<String, Map<String, String>> pages) {
        this.pages = pages;
    }
}
