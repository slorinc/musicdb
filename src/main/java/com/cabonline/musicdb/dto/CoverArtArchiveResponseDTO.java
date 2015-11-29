package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverArtArchiveResponseDTO extends ErrorDTO {

    private List<CoverArtImagesDTO> images;

    public CoverArtArchiveResponseDTO() {
    }

    public CoverArtArchiveResponseDTO(String errorCode, String errorMessage, List<CoverArtImagesDTO> images) {
        super(errorCode, errorMessage);
        this.images = images;
    }

    public List<CoverArtImagesDTO> getImages() {
        return images;
    }

    public void setImages(List<CoverArtImagesDTO> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "CoverArtArchiveResponseDTO{" +
                "images=" + images +
                '}';
    }
}
