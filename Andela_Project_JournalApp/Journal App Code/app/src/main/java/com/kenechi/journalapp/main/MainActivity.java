package com.kenechi.journalapp.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kenechi.journalapp.R;
import com.kenechi.journalapp.account.SignInActivity;
import com.kenechi.journalapp.record.AddRecordActivity;
import com.kenechi.journalapp.record.ViewRecordActivity;
import com.kenechi.journalapp.data.AppDatabase;
import com.kenechi.journalapp.data.RecordEntry;
import com.kenechi.journalapp.util.AppExecutors;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecordAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecordAdapter mAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.journal_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new RecordAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
               // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // COMPLETED (3) get the position from the viewHolder parameter
                        int position = viewHolder.getAdapterPosition();
                        List<RecordEntry> tasks = mAdapter.getRecords();
                        // COMPLETED (4) Call deleteTask in the taskDao with the task at that position
                        mDb.recordDao().deleteTask(tasks.get(position));
                        // COMPLETED (6) Call setupViewModel method to refresh the UI

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newRecord = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(newRecord);
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
//            startActivity(new Intent(MainActivity.this, S.class));
            FirebaseAuth.getInstance().signOut();
            return true;
        }

        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.action_sign_out) {

            FirebaseAuth.getInstance().signOut();

            Intent setupIntent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(setupIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecords().observe(this, new Observer<List<RecordEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecordEntry> recordEntries) {
                mAdapter.setTasks(recordEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, ViewRecordActivity.class);
        intent.putExtra(ViewRecordActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }
}
