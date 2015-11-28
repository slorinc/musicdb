package com.cabonline.musicdb.dto;

import com.cabonline.musicdb.vo.Album;

import java.util.List;

public class MusicDBResponseDTOBuilder {
    private String mbId;
    private String description;
    private List<Album> albums;

    public MusicDBResponseDTOBuilder setMbId(String mbId) {
        this.mbId = mbId;
        return this;
    }

    public MusicDBResponseDTOBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MusicDBResponseDTOBuilder setAlbums(List<Album> albums) {
        this.albums = albums;
        return this;
    }

    public MusicDBResponseDTO createMusicDBResponseDTO() {
        return new MusicDBResponseDTO(mbId, description, albums);
    }
}