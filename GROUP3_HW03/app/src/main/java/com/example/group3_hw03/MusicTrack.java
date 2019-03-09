package com.example.group3_hw03;

import java.io.Serializable;
import java.util.Date;

public class MusicTrack implements Serializable {

    private String trackName;

    private String releaseDate;

    private String artistName;

    private String primaryGenreName;

    private String collectionName;

    private double trackPrice;

    private String collectionPrice;

    private String artworkUrl100;

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public MusicTrack(){
        super();
    }

    public MusicTrack(String trackName, String releaseDate, String artistName, String primaryGenreName, String collectionName, double trackPrice, String collectionPrice, String artworkUrl100) {
        this.trackName = trackName;
        this.releaseDate = releaseDate;
        this.artistName = artistName;
        this.primaryGenreName = primaryGenreName;
        this.collectionName = collectionName;
        this.trackPrice = trackPrice;
        this.collectionPrice = collectionPrice;
        this.artworkUrl100 = artworkUrl100;
    }

    public String getDate() {
        return releaseDate;
    }

    public void setDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    @Override
    public String toString() {
        return "MusicTrack{" +
                "trackName='" + trackName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", artistName='" + artistName + '\'' +
                ", primaryGenreName='" + primaryGenreName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", trackPrice='" + trackPrice + '\'' +
                ", collectionPrice='" + collectionPrice + '\'' +
                '}';
    }
}
