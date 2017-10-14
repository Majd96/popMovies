package com.majd.popmovies2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.majd.popmovies2.adapters.FavoriteAdapter;
import com.majd.popmovies2.adapters.MovieAdapter;
import com.majd.popmovies2.data.MovieContract;
import com.majd.popmovies2.fetchData.FetchMovies;
import com.majd.popmovies2.models.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements MovieAdapter.PosterClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;

    private MovieAdapter adapter;
    GridLayoutManager layoutManager;
    QueryTask queryTask ;
    //________________________________________________________________
    private FavoriteAdapter favoriteAdapter;





    public static final int LOADER_ID = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            movies=new ArrayList<>();//no need to define the type ==>define the type onlt in the top
        }
        else {
            movies=savedInstanceState.getParcelableArrayList("movies");
        }

        if(isNetworkAvailable()){
            recyclerView=(RecyclerView)findViewById(R.id.rv);

            layoutManager=new GridLayoutManager(this,2);

            recyclerView.setHasFixedSize(false);

            recyclerView.setLayoutManager(layoutManager);
            queryTask =  new QueryTask();
            FetchMovies.query="popular";

            try {

                movies = queryTask.execute().get();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            adapter=new MovieAdapter(movies,MainActivity.this,this);
            recyclerView.setAdapter(adapter);

        }
        else{
            Toast.makeText(this,"There is no internet",Toast.LENGTH_LONG).show();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.setting_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.pop_sort){
            if(isNetworkAvailable()){
                recyclerView=(RecyclerView)findViewById(R.id.rv);

                layoutManager=new GridLayoutManager(this,2);

                recyclerView.setHasFixedSize(false);

                recyclerView.setLayoutManager(layoutManager);
                queryTask =  new QueryTask();
                FetchMovies.query="popular";

                try {

                    movies = queryTask.execute().get();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                adapter=new MovieAdapter(movies,MainActivity.this,this);
                recyclerView.setAdapter(adapter);

            }
            else{
                Toast.makeText(this,"There is no internet",Toast.LENGTH_LONG).show();
            }



            return true;

        }
        else if(item.getItemId()==R.id.rate_sort){
            if(isNetworkAvailable()){
                recyclerView=(RecyclerView)findViewById(R.id.rv);

                layoutManager=new GridLayoutManager(this,2);

                recyclerView.setHasFixedSize(false);

                recyclerView.setLayoutManager(layoutManager);
                queryTask =  new QueryTask();
                FetchMovies.query="top_rated";

                try {

                    movies = queryTask.execute().get();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                adapter=new MovieAdapter(movies,MainActivity.this,this);
                recyclerView.setAdapter(adapter);

            }
            else{
                Toast.makeText(this,"There is no internet",Toast.LENGTH_LONG).show();
            }




            return true;


        }
        else if(item.getItemId()==R.id.favorite){



            final RecyclerView favRecyclerView=(RecyclerView)findViewById(R.id.rv);
            if(isNetworkAvailable()){


                layoutManager=new GridLayoutManager(this,2);

                favRecyclerView.setHasFixedSize(false);

                favRecyclerView.setLayoutManager(layoutManager);
                favoriteAdapter=new FavoriteAdapter(this);
                favRecyclerView.setAdapter(favoriteAdapter);
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);



            }
            else{
                Toast.makeText(this,"There is no internet",Toast.LENGTH_LONG).show();
            }



            ItemClickSupport.addTo(favRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v)  {
                    Cursor cursor=null;
                  Log.d("################",position+"");
                    Cursor c=favoriteAdapter.mCursor;
                    c.moveToPosition(position);
                    int pos=c.getInt(c.getColumnIndex(MovieContract.MovieEntry._ID));


                    try {
                         cursor= new FetchTask().execute(pos).get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                        cursor.moveToNext();



                        String tille=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                        String posterPath=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
                        String overview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                        String voiteRange=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
                        String relasedate=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                        String moveId=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                        long id=cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                        cursor.close();
                        Log.d("#############",tille);
                        Log.d("**************",String.valueOf(id));
                        Intent i = new Intent(MainActivity.this,DetailActivity.class);
                        Movie movieObject=new Movie(tille,posterPath,overview,Double.valueOf(voiteRange),relasedate,Long.valueOf(moveId));

                        i.putExtra("myMoveKey",movieObject);



                        startActivity(i);






                }
            });



            return true;



        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_ID:
                return new CursorLoader(this,
                        MovieContract.MovieEntry.CONTENT_URI,
                        new String[]{MovieContract.MovieEntry.COLUMN_POSTER_PATH,MovieContract.MovieEntry._ID},
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " +id);



        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favoriteAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteAdapter.swapCursor(null);

    }


    @Override
    public void onPosterClick(int clickedItemIndex) {

        Intent i = new Intent(this,DetailActivity.class);
        i.putExtra("myMoveKey", movies.get(clickedItemIndex));
        startActivity(i);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies",movies);
        //save thae array in a bundle
    }




    public class FetchTask extends AsyncTask<Integer,Void,Cursor>{



        @Override
        protected Cursor doInBackground(Integer... contexts) {
            Integer l=new Integer(contexts[0]);
            ContentResolver contentResolver =getContentResolver();
            Cursor cursor= contentResolver.query(MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    MovieContract.MovieEntry._ID+" = ?",
                    new String[]{String.valueOf(l)},
                    null);
            return cursor;
        }


    }



}
