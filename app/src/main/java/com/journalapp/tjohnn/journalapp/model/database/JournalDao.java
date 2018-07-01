package com.journalapp.tjohnn.journalapp.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tjohn on 6/29/18.
 */
@Dao
public interface JournalDao {

    @Query("SELECT * FROM journal WHERE google_id = :googleId ORDER BY updated_at DESC")
    Flowable<List<JournalEntry>> loadAllJournals(String googleId);

    @Insert
    void insertJournal(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournal(JournalEntry journalEntry);

    @Delete
    void deleteJournal(JournalEntry journalEntry);

    @Query("SELECT * FROM journal WHERE id = :id")
    Flowable<JournalEntry> getJournalById(int id);

}
