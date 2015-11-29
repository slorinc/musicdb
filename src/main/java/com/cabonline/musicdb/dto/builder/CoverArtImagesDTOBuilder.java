package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.CoverArtImagesDTO;

public class CoverArtImagesDTOBuilder {
    private String image;

    public CoverArtImagesDTOBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public CoverArtImagesDTO createCoverArtImagesDTO() {
        return new CoverArtImagesDTO(image);
    }
}