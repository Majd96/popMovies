package com.majd.popmovies2.models;


import android.os.Parcel;
import android.os.Parcelable;

//save instance in order to the rotation
//the movie class will implement parsable
public class Movie implements Parcelable {
    private long movie_id;
    private String title;
    private String poster_path;
    private String overview;
    private double vote_average;
    private String release_date;

    public Movie(String title, String poster_path, String overview, double vote_average, String release_date, long movie_id){
        this.title=title;this.poster_path=poster_path;
        this.overview=overview;this.vote_average=vote_average;
        this.release_date=release_date;this.movie_id=movie_id;

    }
    //default constructor
    public Movie(){

    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w185/" + poster_path;
    }

    public String getPoster_path_dataBase(){return poster_path;}

    public String getRelase_date() {
        return release_date;
    }

    public long getMovie_id() {return movie_id;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeValue(vote_average);
        parcel.writeString(release_date);
        parcel.writeLong(movie_id);

    }

    private Movie(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
       overview = in.readString();
        vote_average= (Double) in.readValue(Double.class.getClassLoader());
        release_date = in.readString();
        movie_id=in.readLong();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
