package com.majd.popmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by majd on 9/1/17.
 */

public class Review  {

    private String id;
    private String author;
    private String content;

    //default constructor
    public Review(){
        this.id=null;
        this.author=null;
        this.content=null;
    }

    public Review(String id,String author,String content){
        this.id=id;
        this.author=author;
        this.content=content;

    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }


}
