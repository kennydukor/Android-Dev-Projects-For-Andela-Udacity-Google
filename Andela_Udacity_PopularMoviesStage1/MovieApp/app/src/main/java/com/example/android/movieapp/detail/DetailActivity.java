package com.example.android.movieapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private final String posterUrl = "http://image.tmdb.org/t/p/w500//";
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private String mRating;
    private String mPosterUrl;
    private String mId;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView imageView;
    private Intent intent;
    private TextView mOverviewTV;
    private TextView mRatingTV;
    private TextView mRelesasDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        imageView = findViewById(R.id.toolbar_image);
        intent = getIntent();
        mRatingTV = findViewById(R.id.ratingTV);
        mOverviewTV = findViewById(R.id.details_tv);
        mRelesasDateTV = findViewById(R.id.releaseDateTV);

        if (intent != null) {

            mTitle = intent.getStringExtra("title");
            mCollapsingToolbarLayout.setTitle(mTitle);
            mReleaseDate = intent.getStringExtra("date");
            mOverview = intent.getStringExtra("overview");
            mRating = intent.getStringExtra("rating");
            mPosterUrl = intent.getStringExtra("poster");
            mId = intent.getStringExtra("id");



        }

        Picasso.with(this)
                .load(posterUrl+mPosterUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);

        mOverviewTV.setText(mOverview);
        mRelesasDateTV.setText("Released on " + mReleaseDate);
        mRatingTV.setText("Vote Average: " + mRating+"/10");


        // inflate back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

