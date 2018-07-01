package com.journalapp.tjohnn.journalapp.utils;

import com.journalapp.tjohnn.journalapp.JournalApp;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalDatabase;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;

/**
 * Created by Tjohn on 6/25/18.
 *
 * For getting singleton for globally needed objects in the application
 */

public class AppDi {


    private static PreferencesHelper preferencesHelper;
    private static JournalRepository journalRepo;

    /**
     * Gets a signleton instance of PreferencesHelper
     *
     * @return PreferencesHelper preferencesHelper
     */

    public static PreferencesHelper getPreferencesHelper() {
        if(preferencesHelper == null){
            preferencesHelper = new PreferencesHelper(JournalApp.getInstance());
        }
        return preferencesHelper;
    }

    public static JournalRepository journalRepository(){
        if(journalRepo == null){
            journalRepo = new JournalRepository(JournalDatabase.getInstance(JournalApp.getInstance()).journalDao());
        }
        return journalRepo;
    }

    public static AppExecutors executors(){
        return AppExecutors.getInstance();
    }
}
