package com.kennydukor.android.movieapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.kennydukor.android.movieapp.Utilities.GeneralUtilities;
import com.kennydukor.android.movieapp.Utilities.NetworkUtils;
import com.kennydukor.android.movieapp.Utilities.movie_rv_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList_rv;
    private movie_rv_adapter mAdapter; // confirm if this works also
    private List<MovieItemModel> mItemModel;
    private String BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=52a0b5eead877dff884ddaca975675cf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finding the views through their IDs
        movieList_rv = findViewById(R.id.rv_movie);

        // The recycler view in the main_activity.xml show have grid layout
        int mNoOfColumns = GeneralUtilities.calculateNoOfColumns(getApplicationContext()); //automatically selects based on sreen size
        GridLayoutManager layout = new GridLayoutManager(this, mNoOfColumns);
        movieList_rv.setLayoutManager(layout);

//        movieList_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        movieList_rv.setHasFixedSize(true);

        // item model object
        mItemModel = new ArrayList<>();
        load(BASE_URL_POPULAR);
    }


    //--------------------------------------------------------------------------------------
    // populate the adapter
    private void populateRecyclerView() {
        mAdapter = new movie_rv_adapter(getApplicationContext(), mItemModel);
        movieList_rv.setAdapter(mAdapter);
    }


    //---------------------------------------------------------------------------------
    //check network state and populate the UI
    private void load(String sort) {
        if (isOnline()) {
            new MoviesAsyncTask().execute(sort);
        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection, swipe to refresh", Toast.LENGTH_LONG).show();
            //populateRecyclerView();
        }
    }


    //---------------------------------------------------------------------------------
    //The network checking is dependent on this. It checks if there is connection
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();

//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    // ASYNCTASK METHOD-----------------------------------------------------------
    public class MoviesAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
                Log.d("kenechi", response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray array = jsonObject.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    MovieItemModel itemModel = new MovieItemModel(
                            o.getString("poster_path"),
                            o.getString("original_title"),
                            o.getString("release_date"),
                            o.getString("vote_average"),
                            o.getString("overview"),
                            o.getString("id"));
                    mItemModel.add(itemModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String moviesFetched) {
            pDialog.dismiss();
            if (moviesFetched != null && !moviesFetched.equals("")) {
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                populateRecyclerView();
                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }

    }

}