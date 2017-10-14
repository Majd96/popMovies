package com.majd.popmovies2;

import android.os.AsyncTask;

import com.majd.popmovies2.fetchData.FetchReviews;
import com.majd.popmovies2.models.Review;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by majd on 9/1/17.
 */

public class ReviewAsyncTask extends AsyncTask<Void, Void, ArrayList<Review>> {
    public ArrayList<Review> reviews=new ArrayList<>();
    @Override
    protected ArrayList<Review> doInBackground(Void... params) {
        URL url=FetchReviews.buildUrl();



        try {
            String jsonString = FetchReviews.getResponseFromHttpUrl(url);
            reviews = FetchReviews.getJsonFromString(jsonString);
            return reviews;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;

    }
}
