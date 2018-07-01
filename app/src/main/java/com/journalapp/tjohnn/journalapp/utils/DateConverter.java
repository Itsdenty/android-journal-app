package com.journalapp.tjohnn.journalapp.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Tjohn on 6/29/18.
 */

public class DateConverter {
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}