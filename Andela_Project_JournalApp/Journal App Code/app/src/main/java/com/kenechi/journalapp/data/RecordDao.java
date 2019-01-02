package com.kenechi.journalapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM record ORDER BY id")
    LiveData<List<RecordEntry>> loadAllTasks();

    @Insert
    void insertRecord(RecordEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(RecordEntry taskEntry);

    @Delete
    void deleteTask(RecordEntry taskEntry);

    @Query("SELECT * FROM record WHERE id = :id")
    LiveData<RecordEntry> loadRecordById(int id);

}
