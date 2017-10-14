package com.majd.popmovies2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.majd.popmovies2.R;
import com.majd.popmovies2.models.Review;

import java.util.ArrayList;

/**
 * Created by majd on 9/1/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    public ArrayList<Review> myReviews;
    private Context mContext;

    public ReviewAdapter(ArrayList<Review> reviews,Context context){
        myReviews=reviews;
        mContext = context;


    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.review_item,null);
       ReviewViewHolder viewHolder=new ReviewViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return myReviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder

    {
        private TextView author;
        private TextView content;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.review_author);
            content=itemView.findViewById(R.id.review_content);

        }

        public void bind(int position){
            author.setText(String.valueOf(myReviews.get(position).getAuthor()));
            content.setText(String.valueOf(myReviews.get(position).getContent()));

        }
    }
}
