package com.majd.popmovies2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.majd.popmovies2.R;
import com.majd.popmovies2.models.Movie;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import com.squareup.picasso.Picasso;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder> {
    public ArrayList<Movie> myMovies;
    private Context mContext;
    private PosterClickListener posterListener;


    public MovieAdapter(ArrayList<Movie> Movies,PosterClickListener listener , Context context){
        myMovies=Movies;
        mContext = context;


        posterListener=listener;

    }



    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.image_item,null);
        PosterViewHolder viewHolder=new PosterViewHolder(view);
        return viewHolder;
    }






    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return myMovies.size();
    }


    public interface PosterClickListener {
        void onPosterClick(int clickedItemIndex);
    }





    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView moviePoster;

        public PosterViewHolder(View poster){
            super(poster);
            moviePoster= poster.findViewById(R.id.poster);
            poster.setOnClickListener(this);


        }
        public void bind(int position){
            // String imageUri = "https://http://image.tmdb.org/t/p/w185/"+myMovies.get(postion).getPoster_path();
            ImageView ivBasicImage =  moviePoster.findViewById(R.id.poster);
            ivBasicImage.setAdjustViewBounds(true);
            Log.d("movie poster" , position + "");
            Picasso.with(mContext).load(myMovies.get(position).getPoster_path()).resize(185 , 278).into(ivBasicImage);



        }


        @Override
        public void onClick(View view) {
            posterListener.onPosterClick(getAdapterPosition());
        }
    }

}
