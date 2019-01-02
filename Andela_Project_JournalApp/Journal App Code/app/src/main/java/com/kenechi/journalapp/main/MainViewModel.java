package com.kenechi.journalapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kenechi.journalapp.data.AppDatabase;
import com.kenechi.journalapp.data.RecordEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String  TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<RecordEntry>> records;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        records = database.recordDao().loadAllTasks();
    }

    public LiveData<List<RecordEntry>> getRecords() {
        return records;
    }
}
