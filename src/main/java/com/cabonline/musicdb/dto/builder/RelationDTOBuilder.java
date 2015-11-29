package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.RelationDTO;
import com.cabonline.musicdb.dto.UrlDTO;

public class RelationDTOBuilder {
    private String type;
    private UrlDTO url;

    public RelationDTOBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public RelationDTOBuilder setUrl(UrlDTO url) {
        this.url = url;
        return this;
    }

    public RelationDTO createRelationDTO() {
        return new RelationDTO(type, url);
    }
}