package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDTO {

    private String errorCode;
    private String errorMessage;

    public ErrorDTO() {
    }

    public ErrorDTO(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void setError(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorDTO{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
