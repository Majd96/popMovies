package com.majd.popmovies2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.majd.popmovies2.data.MovieContract;
import com.majd.popmovies2.fetchData.FetchMovies;
import com.majd.popmovies2.models.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by majd on 9/4/17.
 */

public class CheckClass {









    public static int isFavorited(Context context, long id) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }









    }



