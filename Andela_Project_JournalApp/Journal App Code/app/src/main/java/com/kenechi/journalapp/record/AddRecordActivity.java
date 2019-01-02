package com.kenechi.journalapp.record;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.kenechi.journalapp.R;
import com.kenechi.journalapp.data.AppDatabase;
import com.kenechi.journalapp.data.RecordEntry;
import com.kenechi.journalapp.util.AppExecutors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddRecordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String current_user_id;
    private FirebaseFirestore firebaseFirestore;
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();


    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    private static final int DEFAULT_TASK_ID = -1;
    private static final String TAG = AddRecordActivity.class.getSimpleName();
    EditText mEditTextTitle;
    EditText mEditTextDesc;

    RadioGroup mRadioGroup;
    Button mButton;

    private int mTaskId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initViews();

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.setFirestoreSettings(settings);


        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
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

    }

    private void populateUI(RecordEntry record) {
        if (record == null) {
            return;
        }

        mEditTextTitle.setText(record.getTitle());
        mEditTextDesc.setText(record.getDescription());
        setPriorityInViews(record.getTag());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    public void onSaveButtonClicked() {

        final String title = mEditTextTitle.getText().toString();
        final String description = mEditTextDesc.getText().toString();
        final int priority = getTagFromViews();
        final Date date = new Date();

        final RecordEntry record = new RecordEntry(title, description, priority, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.recordDao().insertRecord(record);

                } else {
                    //update task
                    record.setId(mTaskId);
                    mDb.recordDao().updateRecord(record);
                }

                uploadToFirebase(title, description, priority);
                finish();
            }
        });



    }

    private void uploadToFirebase(String title, String description, int priority) {
        Map<String, Object> recordMap = new HashMap<>();

        recordMap.put("title", title);
        recordMap.put("description", description);
        recordMap.put("tag", priority);
        recordMap.put("date", FieldValue.serverTimestamp());


        firebaseFirestore.collection("users/"+current_user_id +"/"+"journals").add(recordMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d("Sync", "Successful sync");
                }
            }
        });


    }


    public int getTagFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }


    private void initViews() {
        mEditTextTitle = findViewById(R.id.editTextTaskTitle);
        mEditTextDesc = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);
        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
}
