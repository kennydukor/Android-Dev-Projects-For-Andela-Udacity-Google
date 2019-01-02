package com.kennydukor.android.movieapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class movieDetails extends AppCompatActivity {
//    private String parsedMovieData;
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185//";
    private String parsedPosterURl;
    private String parsedDate;
    private String parsedOverView;
    private String parsedRating;
    private TextView rating;
    private TextView year;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        rating = findViewById(R.id.rating);
        year = findViewById(R.id.year);
        description = findViewById(R.id.description);
        image = findViewById(R.id.imageView);

        Intent intent = getIntent();

        if (intent != null){
            parsedDate = intent.getStringExtra("date");
            parsedOverView = intent.getStringExtra("overview");
            parsedRating = intent.getStringExtra("rating");
            parsedPosterURl = intent.getStringExtra("poster");
        }

        description.setText(parsedOverView);
        year.setText(parsedDate);
        rating.setText(parsedRating + "/ 10");

        Picasso.with(this)
                .load(POSTER_BASE_URL + parsedPosterURl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(image);

    }
}
