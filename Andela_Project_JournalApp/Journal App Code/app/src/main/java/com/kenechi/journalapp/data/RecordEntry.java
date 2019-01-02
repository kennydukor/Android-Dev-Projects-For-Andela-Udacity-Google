package com.kenechi.journalapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "record")
public class RecordEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int tag;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public RecordEntry(String title, String description, int tag, Date updatedAt) {
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.updatedAt = updatedAt;
    }

    public RecordEntry(int id, String title, String description, int tag, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }



}
