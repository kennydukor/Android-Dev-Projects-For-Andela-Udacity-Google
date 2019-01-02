package com.example.android.movieapp.detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapp.BuildConfig;
import com.example.android.movieapp.R;
import com.example.android.movieapp.detail.adapter.ReviewAdapter;
import com.example.android.movieapp.detail.adapter.ThrillerAdapter;
import com.example.android.movieapp.detail.model.ReviewItem;
import com.example.android.movieapp.detail.model.ThrillerItem;
import com.example.android.movieapp.util.ListViewUtility;
import com.example.android.movieapp.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    //&language=en-US
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
    private TextView mReleaseDateTV;

    private TextView mReviewAuthor;
    private TextView mReviewContent;
    private ArrayList<ReviewItem>mReviewItems;
    private List<ThrillerItem> mThrillerItems;
    private ListView mReviewLV;
    private ListView mThrillerLV;

    public String reviews;
    public String thriller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        mReviewItems = new ArrayList<ReviewItem>();
        mThrillerItems = new ArrayList<ThrillerItem>();

        mReviewLV = findViewById(R.id.review_lv);
        mThrillerLV = findViewById(R.id.thriller_lv);

        mReviewAuthor = findViewById(R.id.reviewAuthor);
        mReviewContent = findViewById(R.id.reviewContent);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        imageView = findViewById(R.id.toolbar_image);
        intent = getIntent();
        mRatingTV = findViewById(R.id.ratingTV);
        mOverviewTV = findViewById(R.id.details_tv);
        mReleaseDateTV = findViewById(R.id.releaseDateTV);

        if (intent != null) {

            mTitle = intent.getStringExtra("title");
            mCollapsingToolbarLayout.setTitle(mTitle);
            mReleaseDate = intent.getStringExtra("date");
            mOverview = intent.getStringExtra("overview");
            mRating = intent.getStringExtra("rating");
            mPosterUrl = intent.getStringExtra("poster");
            mId = intent.getStringExtra("id");

            reviews = "https://api.themoviedb.org/3/movie/"+mId+"/reviews?api_key=" + BuildConfig.ApiKey;
            thriller = "https://api.themoviedb.org/3/movie/"+mId+"/videos?api_key=" + BuildConfig.ApiKey;
            load(thriller, reviews);
            //Toast.makeText(getApplicationContext(), mId, Toast.LENGTH_SHORT).show();

        }

        Picasso.with(this)
                .load(posterUrl + mPosterUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);

        mOverviewTV.setText(mOverview);
        mReleaseDateTV.setText("Released on " + mReleaseDate);
        mRatingTV.setText("Vote Average: " + mRating + "/10");

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


    // To check if there is a network connection
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //check network state and populate the UI
    private void load(String thriller, String reviews) {
        if (isOnline()) {
            new DetailActivity.ThrillerAsyncTask().execute(thriller);
            new DetailActivity.ReviewsAsyncTask().execute(reviews);
        } else {
            //populateRecyclerView();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////01
    public class ReviewsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String response = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                response = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d("bukunmi", response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray array = jsonObject.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ReviewItem itemModel = new ReviewItem(
                            o.getString("id").trim(),
                            o.getString("author").trim(),
                            o.getString("content").trim());
                    mReviewItems.add(itemModel);
                    //reviewId.

                    //Log.d("bukunmi", o.getString("id"));

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            //pDialog.dismiss();
            if (s != null && !s.equals("")) {
                ReviewAdapter mReviewAdapter = new ReviewAdapter(DetailActivity.this, R.layout.review_item, mReviewItems);
                mReviewLV.setAdapter(mReviewAdapter);
                ListViewUtility.setListViewHeightBasedOnChildren(mReviewLV);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                Toast.makeText(getApplicationContext(), "could not load reviews", Toast.LENGTH_SHORT).show();
            }
        }

    }







    public class ThrillerAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String response = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                response = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d("bukunmi", response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray array = jsonObject.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ThrillerItem itemModel = new ThrillerItem(
                            o.getString("key"),
                            o.getString("name"),
                            o.getString("site"),
                            o.getString("size"));
                    mThrillerItems.add(itemModel);

                    //Log.d("bukunmi", response.toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            //pDialog.dismiss();
            if (s != null && !s.equals("")) {
                ThrillerAdapter mThrillerAdapter = new ThrillerAdapter(DetailActivity.this, R.layout.trailer_item, mThrillerItems);
                mThrillerLV.setAdapter(mThrillerAdapter);
                ListViewUtility.setListViewHeightBasedOnChildren(mThrillerLV);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                Toast.makeText(getApplicationContext(), "could not load thrillers", Toast.LENGTH_SHORT).show();
            }
        }

    }


}

