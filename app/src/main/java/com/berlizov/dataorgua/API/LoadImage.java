package com.berlizov.dataorgua.API;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.berlizov.dataorgua.Activities.GroupActivity;
import com.berlizov.dataorgua.Group;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by 350z6_000 on 06.11.2015.
 */
public class LoadImage extends AsyncTask<String, String, Bitmap> {
    Group group;
    GroupActivity parent;
    public LoadImage(Group g, GroupActivity groupActivity) {
        group=g;
        parent = groupActivity;
    }

    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {
        if (image != null) {
            parent.imageLoaded(group,image);
        }
    }
}
