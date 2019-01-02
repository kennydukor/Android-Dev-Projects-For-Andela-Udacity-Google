package com.example.android.movieapp.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieapp.R;
import com.example.android.movieapp.detail.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    //Base poster url
    private final String posterUrl = "http://image.tmdb.org/t/p/w185//";

    private List<MovieItemModel> mMovies;
    private Context mContext;

    //Constructor that should receive some objects
    public MovieAdapter(Context context, List<MovieItemModel>movieItemModel) {
        this.mContext = context;
        this.mMovies = movieItemModel;
    }

    //No1 Default method that must be implemented by extending RecyclerView.Adapter
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    //No2 Default method that must be implemented by extending RecyclerView.Adapter
    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        final MovieItemModel model = mMovies.get(position);

        holder.movieTitleTV.setText(model.getMovieOriginalTitle());
        holder.movieReleaseDateTV.setText(model.getMovieReleaseDate());
        Picasso.with(mContext)
                .load(posterUrl+model.getPosterUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.moviePosterIV);

        holder.moviePosterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra("title", model.getMovieOriginalTitle());
                detailIntent.putExtra("date", model.getMovieReleaseDate());
                detailIntent.putExtra("overview", model.getMovieOverview());
                detailIntent.putExtra("rating", model.getMovieRating());
                detailIntent.putExtra("poster", model.getPosterUrl());
                detailIntent.putExtra("id", model.getMovieId());


                detailIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detailIntent);
            }
        });


    }


    //No3 Default method that must be implemented by extending RecyclerView.Adapter
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    //Note: No4 Default method that must be implemented by extending RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView moviePosterIV;
        public TextView movieTitleTV;
        public TextView movieReleaseDateTV;

        public MovieViewHolder(View itemView) {
            super(itemView);

            moviePosterIV = (ImageView) itemView.findViewById(R.id.posterIV);
            movieTitleTV = (TextView) itemView.findViewById(R.id.movieTitleTV);
            movieReleaseDateTV = (TextView) itemView.findViewById(R.id.movieReleaseDateTV);


        }

    }

}
