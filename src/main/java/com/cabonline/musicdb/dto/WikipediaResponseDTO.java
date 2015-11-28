package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikipediaResponseDTO extends ErrorDTO {

    private QueryDTO query;

    public QueryDTO getQuery() {
        return query;
    }

    public void setQuery(QueryDTO query) {
        this.query = query;
    }
}
