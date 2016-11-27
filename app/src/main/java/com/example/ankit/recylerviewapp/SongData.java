package com.example.ankit.recylerviewapp;

/**
 * Created by Ankit on 14-11-2016.
 */

public class SongData {

    String title;
    String path;
    long id;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    long albumId;
    public SongData(String title , String path, long id,long albumId) {
        this.title = title;
        this.path = path;
        this.id = id;
        this.albumId = albumId;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
