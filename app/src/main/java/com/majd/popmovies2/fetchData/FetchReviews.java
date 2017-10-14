package com.majd.popmovies2.fetchData;

import android.net.Uri;

import com.majd.popmovies2.models.Movie;
import com.majd.popmovies2.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by majd on 9/1/17.
 */

public class FetchReviews {
    final static String BASE_URL ="http://api.themoviedb.org/3/movie/";
    final static String PATH="reviews";

    final static String PARAM_KEY="api_key";
    final static String API_KEY="insert here your API key";
    public static String id;

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(id)
                .appendPath(PATH)
                .appendQueryParameter(PARAM_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static ArrayList<Review> getJsonFromString(String jsonString) throws JSONException {
        String id,author,content;


        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray reviewsArray = jsonObject.getJSONArray("results");
        ArrayList<Review> reviewsList = new ArrayList<>();

        for(int i=0;i<reviewsArray.length();i++){
            JSONObject reviewData = reviewsArray.getJSONObject(i);

            id= reviewData.getString("id");
            author= reviewData.getString("author");
            content= reviewData.getString("content");

            reviewsList.add(new Review(id,author,content));
            //Log.d("movie title" , moviesList.get(i).getTitle() );

        }
        return reviewsList;
    }
}
