package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.UrlDTO;

public class UrlDTOBuilder {
    private String resource;

    public UrlDTOBuilder setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public UrlDTO createUrlDTO() {
        return new UrlDTO(resource);
    }
}