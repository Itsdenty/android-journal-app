package com.journalapp.tjohnn.journalapp.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Tjohn on 6/25/18.
 */

public class Utils {
    public static void openFragment(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, String tag, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag)
                .commit();
    }

    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                    @NonNull Fragment fragment, String tag, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public static void logD(String message){
        if(Constants.IS_DEBUGGING){
            Log.d(Constants.APP_TAG, message);
        }
    }
}
