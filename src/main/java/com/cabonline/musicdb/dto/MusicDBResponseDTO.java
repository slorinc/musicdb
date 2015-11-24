package com.cabonline.musicdb.dto;

import com.cabonline.musicdb.vo.Album;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
public class MusicDBResponseDTO {

    public MusicDBResponseDTO(String mbId, String description, List<Album> albums) {
        this.mbId = mbId;
        this.description = description;
        this.albums = albums;
    }



    @JsonProperty("mbid")
    private String mbId;

    private String description;

    private List<Album> albums;

    public String getMbId() {
        return mbId;
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
