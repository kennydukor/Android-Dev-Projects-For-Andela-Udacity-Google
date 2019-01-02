package com.kennydukor.android.movieapp.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennydukor.android.movieapp.MovieItemModel;
import com.kennydukor.android.movieapp.R;
import com.kennydukor.android.movieapp.movieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by kennydukor on 23/08/2018.
 */

public class movie_rv_adapter extends RecyclerView.Adapter<movie_rv_adapter.MovieViewHolder>{
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185//";
    //private static final String IMAGE_SIZE = "w342//";
    private Context context;
    private List<MovieItemModel> mMovies;    // instead of mNumberItems. a list is returned

    //Constructor that should receive some objects
    public movie_rv_adapter(Context context, List<MovieItemModel> movieItemModel) {
        this.context = context;
        this.mMovies = movieItemModel;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForMovieItems = R.layout.movie_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForMovieItems, parent, shouldAttachToParentImmediately);

        //Note, the movie view holder class is defined below
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;

//        or
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
//        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Log.d(TAG, "#" + position);
        final MovieItemModel model = mMovies.get(position);

        holder.movieTitle_holderClass.setText(model.getMovieOriginalTitle());
        holder.movieReleaseDate_holderClass.setText(model.getMovieReleaseDate());
        Picasso.with(context)
                .load(POSTER_BASE_URL + model.getPosterUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.posterImage_holderClass);

        holder.posterImage_holderClass.setOnClickListener(new View.OnClickListener() {
            @Override
            //Ask question on this it get interesting here. we dont need another model because of this
            public void onClick(View view) {
                Intent detailsIntent = new Intent(context, movieDetails.class);
                // This is a boiler plate code
                detailsIntent.putExtra("title", model.getMovieOriginalTitle());
                detailsIntent.putExtra("date", model.getMovieReleaseDate());
                detailsIntent.putExtra("overview", model.getMovieOverview());
                detailsIntent.putExtra("rating", model.getMovieVoteAverage());
                detailsIntent.putExtra("id", model.getMovieId());
                detailsIntent.putExtra("poster", model.getPosterUrl());

//                //this should be better
//                detailsIntent.putExtra("poster", model.getPosterUrl());
//                detailsIntent.putExtra("movies", model);

                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // note, this class was called above
    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImage_holderClass;
        TextView movieTitle_holderClass;
        TextView movieReleaseDate_holderClass;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieTitle_holderClass = itemView.findViewById(R.id.movieTitle_item);
            movieReleaseDate_holderClass = itemView.findViewById(R.id.ReleaseDate_item);
            posterImage_holderClass = itemView.findViewById(R.id.poster_item);
        }
    }
}