package com.example.asanaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileInputStream;

/**
 * Created by Usser on 14.11.2016.
 */
public class ImageDownload extends AsyncTask<String, Void, Bitmap> {
    final public static String TAG = "DownloadWebpageTask";
    ImageView iv;
    Context c;
    public ImageDownload (ImageView iv, Context c) {
        this.iv = iv;
        this.c = c;
    }
    //open the image from file and display it
    @Override
    protected Bitmap doInBackground(String... strings) {
        String myurl = strings[0];
        try {
            FileInputStream inputStream = c.openFileInput(myurl);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap bm) {
        iv.setImageBitmap(bm);
    }


}
