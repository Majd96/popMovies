package com.majd.popmovies2;


import android.os.AsyncTask;

import com.majd.popmovies2.fetchData.FetchMovies;
import com.majd.popmovies2.models.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class QueryTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
    ArrayList<Movie> movieArrayList;

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {

        URL url = FetchMovies.buildUrl();
        try {
            String jsonString = FetchMovies.getResponseFromHttpUrl(url);
            movieArrayList = FetchMovies.getJsonFromString(jsonString);
            return movieArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}