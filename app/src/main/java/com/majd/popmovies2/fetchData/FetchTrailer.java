package com.majd.popmovies2.fetchData;

import android.net.Uri;

import com.majd.popmovies2.models.Trailer;

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

public class FetchTrailer {

    final static String BASE_URL ="http://api.themoviedb.org/3/movie/";
    final static String PATH="videos";

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


    public static ArrayList<Trailer> getJsonFromString(String jsonString) throws JSONException {
        String id,key,name;


        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray trailersArray = jsonObject.getJSONArray("results");
        ArrayList<Trailer> trailersList = new ArrayList<>();

        for(int i=0;i<trailersArray.length();i++){
            JSONObject trailerData = trailersArray.getJSONObject(i);

            id= trailerData.getString("id");
            key= trailerData.getString("key");
            name= trailerData.getString("name");

            trailersList.add(new Trailer(id,key,name));


        }
        return trailersList;
    }
}
