package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.QueryDTO;
import com.cabonline.musicdb.dto.WikipediaResponseDTO;

public class WikipediaResponseDTOBuilder {
    private String errorCode;
    private String errorMessage;
    private QueryDTO query;

    public WikipediaResponseDTOBuilder setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public WikipediaResponseDTOBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public WikipediaResponseDTOBuilder setQuery(QueryDTO query) {
        this.query = query;
        return this;
    }

    public WikipediaResponseDTO createWikipediaResponseDTO() {
        return new WikipediaResponseDTO(errorCode, errorMessage, query);
    }
}