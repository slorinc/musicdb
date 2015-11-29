package com.cabonline.musicdb.dto.builder;

import com.cabonline.musicdb.dto.ReleaseDTO;

public class ReleaseDTOBuilder {
    private String title;
    private String primaryType;
    private String mdId;

    public ReleaseDTOBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ReleaseDTOBuilder setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
        return this;
    }

    public ReleaseDTOBuilder setMdId(String mdId) {
        this.mdId = mdId;
        return this;
    }

    public ReleaseDTO createReleaseDTO() {
        return new ReleaseDTO(title, primaryType, mdId);
    }
}