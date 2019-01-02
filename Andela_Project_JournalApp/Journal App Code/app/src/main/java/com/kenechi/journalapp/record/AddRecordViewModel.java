package com.kenechi.journalapp.record;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.kenechi.journalapp.data.AppDatabase;
import com.kenechi.journalapp.data.RecordEntry;

public class AddRecordViewModel  extends ViewModel{

    private LiveData<RecordEntry> record;

    public AddRecordViewModel(AppDatabase database, int recordId) {
        record = database.recordDao().loadRecordById(recordId);
    }

    public LiveData<RecordEntry> getRecord() {
        return record;
    }
}
