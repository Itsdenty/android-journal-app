package com.journalapp.tjohnn.journalapp;

import android.app.Application;

/**
 * Created by Tjohn on 6/24/18.
 */

public class JournalApp extends Application {

    private static JournalApp  app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }



    public static JournalApp getInstance(){
        return app;
    }

}
