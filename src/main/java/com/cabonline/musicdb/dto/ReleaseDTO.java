package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseDTO {

    public ReleaseDTO() {
    }

    public ReleaseDTO(String title, String primaryType, String mdId) {
        this.title = title;
        this.primaryType = primaryType;
        this.mdId = mdId;
    }

    private String title;

    @JsonProperty("primary-type")
    private String primaryType;

    @JsonProperty("id")
    private String mdId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getMdId() {
        return mdId;
    }

    public void setMdId(String mdId) {
        this.mdId = mdId;
    }

    @Override
    public String toString() {
        return "ReleasesDTO{" +
                "title='" + title + '\'' +
                ", primaryType='" + primaryType + '\'' +
                ", mdId='" + mdId + '\'' +
                '}';
    }
}

