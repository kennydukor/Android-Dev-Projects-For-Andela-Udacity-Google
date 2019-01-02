package com.kenechi.journalapp.record;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kenechi.journalapp.R;
import com.kenechi.journalapp.data.AppDatabase;
import com.kenechi.journalapp.data.RecordEntry;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class ViewRecordActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    private static final int DEFAULT_TASK_ID = -1;
    private static final String TAG = ViewRecordActivity.class.getSimpleName();


    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mDescTextView;
    private TextView mDateTextView;

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        mToolbar = (Toolbar) findViewById(R.id.view_record_toolbar);
        setSupportActionBar(mToolbar);
        mCollapsingToolbarLayout = findViewById(R.id.record_toolbar_layout);

        mDescTextView = findViewById(R.id.recordContent);
        mDateTextView = findViewById(R.id.date_text_view);


        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            //mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AddRecordViewModelFactory factory = new AddRecordViewModelFactory(mDb, mTaskId);
                final AddRecordViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddRecordViewModel.class);

                viewModel.getRecord().observe(this, new Observer<RecordEntry>() {
                    @Override
                    public void onChanged(@Nullable RecordEntry recordEntry) {
                        viewModel.getRecord().removeObserver(this);
                        populateUI(recordEntry);
                    }
                });

            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch AddTaskActivity adding the itemId as an extra in the intent
                Intent intent = new Intent(ViewRecordActivity.this, AddRecordActivity.class);
                intent.putExtra(AddRecordActivity.EXTRA_TASK_ID, mTaskId);
                startActivity(intent);
            }
        });

    }



    private void populateUI(RecordEntry record) {
        if (record == null) {
            return;
        }

        mCollapsingToolbarLayout.setTitle(record.getTitle());
        mDescTextView.setText(record.getDescription());
        String date = dateFormat.format(record.getUpdatedAt());
        mDateTextView.setText(date);

        setPriorityInViews(record.getTag());
    }


    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                mCollapsingToolbarLayout.setContentScrimResource(R.color.materialRed);
                mCollapsingToolbarLayout.setBackgroundResource(R.color.materialRed);
                break;
            case PRIORITY_MEDIUM:
                mCollapsingToolbarLayout.setContentScrimResource(R.color.materialOrange);
                mCollapsingToolbarLayout.setBackgroundResource(R.color.materialOrange);
                break;
            case PRIORITY_LOW:
                mCollapsingToolbarLayout.setContentScrimResource(R.color.materialYellow);
                mCollapsingToolbarLayout.setBackgroundResource(R.color.materialYellow);
        }
    }
}
