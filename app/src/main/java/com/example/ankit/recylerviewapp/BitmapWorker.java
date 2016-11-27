package com.example.ankit.recylerviewapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Ankit on 14-11-2016.
 */

public class BitmapWorker extends AsyncTask<Long , Void , Bitmap> {
    private WeakReference<ImageView> imageViewWeakReference;
    private byte[] array;
    long ID=0;
   private String s;
    private Context context;
    public BitmapWorker(ImageView imageView, byte[] array, long id, String s, Context c) {
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.array = array;
        ID = id;
        this.s = s;
        context = c;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(imageViewWeakReference != null && bitmap != null) {
            ImageView image = imageViewWeakReference.get();
            if( image != null) {
                image.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected Bitmap doInBackground(Long... params) {
        MainActivity.metadataRetriever.setDataSource(context, Uri.parse(s));
        array = MainActivity.metadataRetriever.getEmbeddedPicture();
        if(array != null) {
            Bitmap bitmap = decodeBitmapImage(array, 50, 50);
            return bitmap;
        }
        else
            return null;
       // MainActivity.setBitmapIntoCache(String.valueOf(ID), bitmap);


    }

    public Bitmap decodeBitmapImage(byte[] arr , int reqWidth ,int  reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
        options.inSampleSize = getInSampleSize(reqHeight , reqWidth,options);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
    }
    private int getInSampleSize(int reqHeight, int reqWidth, BitmapFactory.Options options) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize=1;
        if(height > reqHeight || width > reqWidth) {
            int halfHeight = height/2;
            int halfWidth = width/2;
            while (((halfHeight/sampleSize)>=reqHeight) && (halfWidth/sampleSize)>= reqWidth) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }
}
