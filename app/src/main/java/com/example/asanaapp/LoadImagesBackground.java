package com.example.asanaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Usser on 18.11.2016.
 */
public class LoadImagesBackground extends AsyncTask<String,Void,String> {
    Context c;
    String imageId;
    public LoadImagesBackground(Context c, String imageId) {
        this.c = c;
        this.imageId = imageId;
    }

    @Override
    protected String doInBackground(String... strings) {

        //download images and store them in files named by their unique objectIDs
                            URL url = null;
                            try {
                                url = new URL(strings[0]);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setDoInput(true);
                                conn.connect();
                                InputStream  is = conn.getInputStream();
                                Bitmap b = BitmapFactory.decodeStream(is);
                                FileOutputStream fos = c.openFileOutput(imageId,c.MODE_WORLD_READABLE);
                                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.close();
                                is.close();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (ProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }





        return null;
    }
}
