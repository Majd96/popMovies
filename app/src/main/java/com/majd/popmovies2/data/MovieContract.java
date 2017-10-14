package com.majd.popmovies2.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by majd on 8/29/17.
 */

public class MovieContract {
    //to know which content provider to access
    public static final String CONTENT_AUTHORITY = "com.majd.popmovies2";

    public static final Uri BASE_CONENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        //paths for the data that we want the content provider to fetch from the database
    public static final String PATH_MOVIE = "movie";

    //create an inner class that implements the basecolumns for each table in the data base
    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI=BASE_CONENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movieTable";
        public static final String COLUMN_MOVIE_ID="movieId";

        public static final String COLUMN_TITLE = "movieTitle";

        public static final String COLUMN_RELEASE_DATE = "ReleaseDate";

        public static final String COLUMN_VOTE_AVERAGE="voteAverage";

        public static final String COLUMN_OVERVIEW="overview";

        public static final String COLUMN_POSTER_PATH="posterPath";

        public static Uri buildMovieUri(long id){

            return ContentUris.withAppendedId(CONTENT_URI, id);

        }









    }

}
