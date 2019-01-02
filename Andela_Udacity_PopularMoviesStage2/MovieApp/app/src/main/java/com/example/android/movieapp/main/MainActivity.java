package com.example.android.movieapp.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.movieapp.BuildConfig;
import com.example.android.movieapp.R;
import com.example.android.movieapp.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    // Create layout objects
    private SwipeRefreshLayout mSwipe;
    private Toolbar mToolbar;
    private RecyclerView mRV;
    private RecyclerView.Adapter mAdapter;
    private List<MovieItemModel> mItemModel;

    //346910
    // /movie/{id}/videos

// href="https://www.youtube.com/watch?v=WBb3fojgW0Q" target="_blank"
    // reviews https://api.themoviedb.org/3/movie/19404/reviews?api_key=52a0b5eead877dff884ddaca975675cf

    // correct https://api.themoviedb.org/3/movie/19404/videos?api_key=52a0b5eead877dff884ddaca975675cf&language=en-US
//https://api.themoviedb.org/3/movie/{19404}/videos?api_key=52a0b5eead877dff884ddaca975675cf&language=en-US
    //http://api.themoviedb.org/3/movie/{346910}/videos?api_key=52a0b5eead877dff884ddaca975675cf"
    //http://api.themoviedb.org/3/movie/top_rated?api_key=52a0b5eead877dff884ddaca975675cf"
    private String BASE_URL_HIGHEST_RATED= "http://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.ApiKey;
    private String BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.ApiKey;


    private String mUrl;

    //JSON Node Names
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make toolbar action bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mUrl = BASE_URL_POPULAR;

        // Initialize swipe refresh
        mSwipe = findViewById(R.id.swipeRL);
        // make network request when swiped
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mItemModel != null){
                    mItemModel.clear();
                }
                load(mUrl);
                mAdapter.notifyDataSetChanged();
                mSwipe.setRefreshing(false);
            }
        });
        mSwipe.setColorSchemeColors(getResources().getColor(R.color.refresh_progress_1)
                , getResources().getColor(R.color.refresh_progress_2)
                , getResources().getColor(R.color.refresh_progress_3));


        mRV = findViewById(R.id.rV);

        mRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRV.setHasFixedSize(true);


        // item model object
        mItemModel = new ArrayList<>();

        mAdapter = new MovieAdapter(getApplicationContext(), mItemModel);
        mRV.setAdapter(mAdapter);


        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_sort);
        mToolbar.setOverflowIcon(drawable);

        load(BASE_URL_POPULAR);

        //new MoviesAsyncTask().execute(BASE_URL_POPULAR);
    }

    //check network state and populate the UI
    private void load(String sort){
        if (isOnline()){
            new MoviesAsyncTask().execute(sort);
        }else {
            Toast.makeText(getApplicationContext(), "please check internet connection, swipe to refresh", Toast.LENGTH_SHORT).show();
            //populateRecyclerView();
        }
    }

    //Toolbar options menu created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //handle options menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_most_popular) {
            mUrl = BASE_URL_POPULAR;
            if (mItemModel != null){
                mItemModel.clear();
            }
            load(mUrl);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_highest_rated) {
            mUrl = BASE_URL_HIGHEST_RATED;
            if (mItemModel != null){
                mItemModel.clear();
            }
            load(BASE_URL_HIGHEST_RATED);
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // To check if there is a network connection
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    //populate the adapter with item model objects
    private void populateRecyclerView() {
        // specify an adapter (see also next example)
        mAdapter = new MovieAdapter(getApplicationContext(), mItemModel);
        mRV.setAdapter(mAdapter);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    public class MoviesAsyncTask extends AsyncTask<String,Void,String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null ;
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

                for (int i = 0; i < array.length(); i++){
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
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if (s != null && !s.equals("")) {
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

