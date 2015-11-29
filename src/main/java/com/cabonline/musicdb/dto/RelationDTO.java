package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationDTO {

    private String type;
    private UrlDTO url;

    public RelationDTO() {
    }

    public RelationDTO(String type, UrlDTO url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UrlDTO getUrl() {
        return url;
    }

    public void setUrl(UrlDTO url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RelationDTO{" +
                "type='" + type + '\'' +
                ", url=" + url +
                '}';
    }
}
