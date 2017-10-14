package com.majd.popmovies2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majd.popmovies2.R;
import com.majd.popmovies2.models.Trailer;

import java.util.ArrayList;

/**
 * Created by majd on 9/1/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private ArrayList<Trailer> trailers;
    private Context context;


    public TrailerAdapter(ArrayList<Trailer> trailers,Context context){
        this.trailers=trailers;

        this.context=context;

    }



    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.trailer_item,null);
        TrailerViewHolder viewHolder=new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder

    {
        private TextView trailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailer=itemView.findViewById(R.id.trailer_name);


        }


        public void bind(int position){
            int index=position+1;
            trailer.setText("Trailer "+index);



        }
    }
}
