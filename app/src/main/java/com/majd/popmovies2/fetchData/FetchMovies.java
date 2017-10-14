package com.majd.popmovies2.fetchData;

import android.net.Uri;
import com.majd.popmovies2.models.Movie;
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

public class FetchMovies {
    final static String BaseUrl ="http://api.themoviedb.org/3/movie/";
    public static String query="popular";

    final static String PARAM_key="api_key";
    final static String api_Key="insert here your API key";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BaseUrl).buildUpon()
                .appendPath(query)
                .appendQueryParameter(PARAM_key, api_Key)
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

    public static ArrayList<Movie> getJsonFromString(String jsonString) throws JSONException {
        String title,poster_path,overview,release_date;
        double vote_average;long movie_id;


        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray moviesArray = jsonObject.getJSONArray("results");
        ArrayList<Movie> moviesList = new ArrayList<>();

        for(int i=0;i<moviesArray.length();i++){
            JSONObject movieData = moviesArray.getJSONObject(i);

            vote_average= movieData.getDouble("vote_average");
            title = movieData.getString("title");

            poster_path = movieData.getString("poster_path");

            overview = movieData.getString("overview");
            release_date = movieData.getString("release_date");
            movie_id=movieData.getLong("id");
            moviesList.add(new Movie(title,poster_path,overview,vote_average,release_date,movie_id));
            //Log.d("movie title" , moviesList.get(i).getTitle() );

        }
        return moviesList;
    }

}
