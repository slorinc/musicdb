package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.ErrorDTO;

public class ErrorDTOBuilder {
    private String errorCode;
    private String errorMessage;

    public ErrorDTOBuilder setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorDTOBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ErrorDTO createErrorDTO() {
        return new ErrorDTO(errorCode, errorMessage);
    }
}