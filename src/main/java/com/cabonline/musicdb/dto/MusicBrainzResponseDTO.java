package com.cabonline.musicdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicBrainzResponseDTO extends RestResponse {

    private String name;

    @JsonProperty("release-groups")
    private List<ReleasesDTO> releasesList;

    @JsonProperty("relations")
    private List<RelationDTO> relationList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReleasesDTO> getReleasesList() {
        return releasesList;
    }

    public void setReleasesList(List<ReleasesDTO> releasesList) {
        this.releasesList = releasesList;
    }

    public List<RelationDTO> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<RelationDTO> relationList) {
        this.relationList = relationList;
    }
}
