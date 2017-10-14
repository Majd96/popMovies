package com.majd.popmovies2;

import android.os.AsyncTask;

import com.majd.popmovies2.fetchData.FetchTrailer;
import com.majd.popmovies2.models.Trailer;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by majd on 9/1/17.
 */

public class TrailerAsyncTask extends AsyncTask<Void, Void, ArrayList<Trailer>> {
    public ArrayList<Trailer> trailers=new ArrayList<>();
    @Override
    protected ArrayList<Trailer> doInBackground(Void... voids) {

        URL url= FetchTrailer.buildUrl();



        try {
            String jsonString = FetchTrailer.getResponseFromHttpUrl(url);
            trailers = FetchTrailer.getJsonFromString(jsonString);
            return trailers;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }
}
