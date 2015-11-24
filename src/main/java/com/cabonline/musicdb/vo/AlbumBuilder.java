package com.cabonline.musicdb.vo;

public class AlbumBuilder {
    private String title;
    private String id;
    private String image;

    public AlbumBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public AlbumBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public AlbumBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public Album createAlbum() {
        return new Album(title, id, image);
    }
}