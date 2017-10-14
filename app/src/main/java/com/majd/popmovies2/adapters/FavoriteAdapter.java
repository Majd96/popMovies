package com.majd.popmovies2.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.majd.popmovies2.R;
import com.majd.popmovies2.data.MovieContract;
import com.squareup.picasso.Picasso;


/**
 * Created by majd on 9/3/17.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavouriteViewHolder> {

    private final Context mContext;
    public Cursor mCursor;


    public FavoriteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.image_item, parent, false);
        view.setFocusable(true);
        return new FavouriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String posterPath=mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        Log.d("###ID##",mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry._ID))+"");

        holder.bind(position,posterPath);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;


        public FavouriteViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.poster);
            //itemView.setTag(1,);
        }



        public void bind(int position,String posterpath){
            // String imageUri = "https://http://image.tmdb.org/t/p/w185/"+myMovies.get(postion).getPoster_path();
            ImageView ivBasicImage =  moviePoster.findViewById(R.id.poster);
            ivBasicImage.setAdjustViewBounds(true);
            Log.d("movie poster" , position + "");
            Log.d("###cursor##",mCursor.getPosition()+"");
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w185/"+posterpath).resize(185 , 556).into(ivBasicImage);





        }
    }
}
