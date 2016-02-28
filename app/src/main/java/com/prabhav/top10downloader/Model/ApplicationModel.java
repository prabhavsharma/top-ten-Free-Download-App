package com.prabhav.top10downloader.Model;

/**
 * Created by ps292 on 2/27/2016.
 */
public class ApplicationModel {
    private String name;
    private String artist;
    private String release;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "Name:" +  getName()+"\n"+
                "Artist :" +  getArtist() + "\n" +
                "Release Date :" + getRelease() + "\n";
    }
}
