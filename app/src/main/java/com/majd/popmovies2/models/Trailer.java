package com.majd.popmovies2.models;

/**
 * Created by majd on 9/1/17.
 */

public class Trailer {
    private String movieID;
    private String videoKey;
    private String videoName;
    //default constructor
    public Trailer(){
        this.movieID=null;
        this.videoKey=null;
        this.videoName=null;

    }

    public Trailer(String id,String key,String name){
        movieID=id;
        videoKey=key;
        videoName=name;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
