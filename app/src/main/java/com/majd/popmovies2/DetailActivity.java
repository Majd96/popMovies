package com.majd.popmovies2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.majd.popmovies2.adapters.MovieAdapter;
import com.majd.popmovies2.adapters.ReviewAdapter;
import com.majd.popmovies2.adapters.TrailerAdapter;
import com.majd.popmovies2.data.MovieContract;
import com.majd.popmovies2.fetchData.FetchReviews;
import com.majd.popmovies2.fetchData.FetchTrailer;
import com.majd.popmovies2.models.Movie;
import com.majd.popmovies2.models.Review;
import com.majd.popmovies2.models.Trailer;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by majd on 8/15/17.
 */

public class DetailActivity extends AppCompatActivity {
//for the reviews
    public  ArrayList<Review> reviews;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    LinearLayoutManager layoutManager;
    ReviewAsyncTask reviewAsyncTask ;
//_______________________________________________________
    //for the trailers

    public  ArrayList<Trailer> trailers;
    private RecyclerView recyclerView_traier;
    private TrailerAdapter trailerAdapter;
    LinearLayoutManager layoutManager_trailer;
    TrailerAsyncTask trailerAsyncTask ;
//_____________________________________________________________
    //for movie's detail
    private TextView tvTitle;
    private ImageView ivPoster;
    private TextView tvRelaseDtae;
    private TextView tvVoitRating;
    private TextView tvOveriew;
    private Movie movieObject;
    private Button favButton;







    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        tvTitle=(TextView)findViewById(R.id.title);
        ivPoster=(ImageView)findViewById(R.id.poster);
        tvRelaseDtae=(TextView)findViewById(R.id.release_date);
        tvVoitRating=(TextView)findViewById(R.id.voit_rate);
        tvOveriew=(TextView)findViewById(R.id.overview);
        favButton=(Button)findViewById(R.id.fav_button);


        movieObject = (Movie) getIntent().getParcelableExtra("myMoveKey");
        if (CheckClass.isFavorited(this,movieObject.getMovie_id())==1)
        {
            favButton.setText("REMOVE FROM FAVORITE");
        }
        else
        {
            favButton.setText("MARK AS FAVORITE");
        }
        tvTitle.setText(movieObject.getTitle().toString());

        Picasso.with(getApplicationContext()).load(movieObject.getPoster_path()).into(ivPoster);
        tvRelaseDtae.setText(movieObject.getRelase_date().toString());
        tvVoitRating.setText(movieObject.getVote_average()+"/10");
        tvOveriew.setText(movieObject.getOverview().toString());



        //_______________________________for the reviews____________________________________
        recyclerView=(RecyclerView)findViewById(R.id.review_rv);

        layoutManager=new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(layoutManager);
        reviewAsyncTask =  new ReviewAsyncTask();
        FetchReviews.id=String.valueOf(movieObject.getMovie_id());


        try {

            reviews = reviewAsyncTask.execute().get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        reviewAdapter=new ReviewAdapter(reviews,this);
        recyclerView.setAdapter(reviewAdapter);

        //______________________________for the trailers_______________________________________

        recyclerView_traier=(RecyclerView)findViewById(R.id.trailer_rv);

        layoutManager_trailer=new LinearLayoutManager(this);

        recyclerView_traier.setHasFixedSize(false);

        recyclerView_traier.setLayoutManager(layoutManager_trailer);
        trailerAsyncTask=  new TrailerAsyncTask();
        FetchTrailer.id=String.valueOf(movieObject.getMovie_id());


        try {

            trailers = trailerAsyncTask.execute().get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        trailerAdapter=new TrailerAdapter(trailers,this);
        recyclerView_traier.setAdapter(trailerAdapter);


        ItemClickSupport.addTo(recyclerView_traier).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Uri uri=Uri.parse("https://www.youtube.com/watch?v="+trailers.get(position).getVideoKey());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });


    }

    public void onClickFavorite(View view) {
        String buttonText=new String(favButton.getText().toString());
       if (buttonText.equals("MARK AS FAVORITE")) {
            ContentValues cv=new ContentValues();
            cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,String.valueOf(movieObject.getMovie_id()));
            cv.put(MovieContract.MovieEntry.COLUMN_TITLE,movieObject.getTitle());
           cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,movieObject.getRelase_date());
            cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,movieObject.getVote_average());
            cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,movieObject.getOverview());
            cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,movieObject.getPoster_path_dataBase());

            Uri uri=getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,cv);


            favButton.setText("REMOVE FROM FAVORITE");

            Toast.makeText(this,"Added to favorites",Toast.LENGTH_LONG).show();

        }
        else if(buttonText.equals("REMOVE FROM FAVORITE")){
           getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                   MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?",
                   new String[]{String.valueOf(movieObject.getMovie_id())});

           Toast.makeText(this,"Removed from favorites",Toast.LENGTH_LONG).show();
           favButton.setText("MARK AS FAVORITE");
           //getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);





       }
    }
}

