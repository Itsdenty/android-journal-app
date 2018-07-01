package com.journalapp.tjohnn.journalapp.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.journalapp.tjohnn.journalapp.utils.DateConverter;

/**
 * Created by Tjohn on 6/29/18.
 */
@Database(entities = {JournalEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class JournalDatabase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "journal_db";
    private static JournalDatabase mInstance;

    public static JournalDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        JournalDatabase.class, JournalDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return mInstance;
    }

    public abstract JournalDao journalDao();

}
