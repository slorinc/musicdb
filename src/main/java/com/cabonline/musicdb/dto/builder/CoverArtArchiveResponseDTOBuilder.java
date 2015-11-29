package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.CoverArtArchiveResponseDTO;
import com.cabonline.musicdb.dto.CoverArtImagesDTO;

import java.util.List;

public class CoverArtArchiveResponseDTOBuilder {
    private String errorCode;
    private String errorMessage;
    private List<CoverArtImagesDTO> images;

    public CoverArtArchiveResponseDTOBuilder setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CoverArtArchiveResponseDTOBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public CoverArtArchiveResponseDTOBuilder setImages(List<CoverArtImagesDTO> images) {
        this.images = images;
        return this;
    }

    public CoverArtArchiveResponseDTO createCoverArtArchiveResponseDTO() {
        return new CoverArtArchiveResponseDTO(errorCode, errorMessage, images);
    }
}