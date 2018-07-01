package com.journalapp.tjohnn.journalapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tjohn on 7/1/18.
 */

public class DownloadPictureTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadPictureTask(ImageView bmImage) {
        this.imageView = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        Utils.logD("Downloading picture");
        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Utils.logD(e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if(result != null) imageView.setImageBitmap(result);
        else Utils.logD("Null response from background task");
    }
}