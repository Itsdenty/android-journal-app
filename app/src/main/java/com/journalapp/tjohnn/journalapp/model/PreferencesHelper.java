package com.journalapp.tjohnn.journalapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tjohn on 6/24/18.
 */

public class PreferencesHelper {


    private static final String APP_PREF_NAME = "JournalApp";
    public static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";

    private SharedPreferences preferences;

    public static PreferencesHelper getInstance(Context context){
        return new PreferencesHelper(context);
    }

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(APP_PREF_NAME, MODE_PRIVATE);
    }

    public String getStringPreference(String key){
        return preferences.getString(key, null);
    }

    public int getIntPreference(String key){
        return preferences.getInt(key, 0);
    }


    public long getLongPreference(String key){
        return preferences.getLong(key, 0);
    }


    public boolean getBooleanPreferenceFalse(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }

    public boolean putString(String key, String value){
        return preferences.edit().putString(key, value).commit();
    }

    public boolean putBoolean(String key, boolean value){
        return preferences.edit().putBoolean(key, value).commit();
    }

    public boolean putInteger(String key, int value){
        return preferences.edit().putInt(key, value).commit();
    }

}
