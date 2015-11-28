package com.cabonline.musicdb.dto;

import com.cabonline.musicdb.vo.Album;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicDBResponseDTO {

    @JsonProperty("mbid")
    private String mbId;

    private String description;

    private List<Album> albums;

    private List<ErrorDTO> errors;


    public MusicDBResponseDTO(String mbId, String description, List<Album> albums, List<ErrorDTO> errors) {
        this.mbId = mbId;
        this.description = description;
        this.albums = albums;
        this.errors = errors;
    }

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

    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDTO> errors) {
        this.errors = errors;
    }
}
