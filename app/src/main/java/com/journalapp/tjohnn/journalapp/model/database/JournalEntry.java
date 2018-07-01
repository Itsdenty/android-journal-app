package com.journalapp.tjohnn.journalapp.model.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Tjohn on 6/29/18.
 */

@Entity(tableName = "journal")
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    @ColumnInfo(name = "google_id")
    private String googleId;

    @Ignore
    public JournalEntry(String title, String description, Date updatedAt) {
        this.title = title;
        this.description = description;
        this.updatedAt = updatedAt;
    }
    @Ignore
    public JournalEntry(String title, String description, Date updatedAt, String googleId) {
        this.title = title;
        this.description = description;
        this.updatedAt = updatedAt;
        this.googleId = googleId;
    }

    public JournalEntry(int id, String title, String description, Date updatedAt, String googleId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.updatedAt = updatedAt;
        this.googleId = googleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descridption) {
        this.description = descridption;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
