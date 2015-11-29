package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlDTO {

    public UrlDTO() {
    }

    public UrlDTO(String resource) {
        this.resource = resource;
    }

    private String resource;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "UrlDTO{" +
                "resource='" + resource + '\'' +
                '}';
    }
}
