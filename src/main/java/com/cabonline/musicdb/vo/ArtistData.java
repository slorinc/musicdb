package com.cabonline.musicdb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistData {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
