package com.example.ankit.recylerviewapp;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Ankit on 16-11-2016.
 */

public class ImageClassLoader extends AsyncTask<Void , Void, Void> {
    private byte[]array;
    private   MediaMetadataRetriever metadataRetriever;
    private  Context c;
    private ImageView imageView;
    private String s;
    private View view;
    public ImageClassLoader(Context context, ImageView imageView, String s,View view) {
        this.metadataRetriever = new MediaMetadataRetriever();
        this.c = context;
        this.imageView = imageView;
        this.s = s;
        this.view = view;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Uri uri = Uri.parse(s);
        metadataRetriever.setDataSource(c,uri);
        array = metadataRetriever.getEmbeddedPicture();
        return null;
    }

    @Override
    protected void onPreExecute() {
        Glide.clear(view);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Glide.with(c).load(array).into(imageView);
    }

}
