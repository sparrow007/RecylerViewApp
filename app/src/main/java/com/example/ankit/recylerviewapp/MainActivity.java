package com.example.ankit.recylerviewapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import java.util.ArrayList;

/**
 * Created by Ankit on 14-11-2016.
 */


public class MainActivity extends AppCompatActivity {
    private  final static int READ_EXTERNAL_STORAGE_RESULT=0;
    private RecyclerView songList;
    ArrayList<SongData> songs;
    public  static MediaMetadataRetriever metadataRetriever;
    private static LruCache<String , Bitmap> mMemoryCache;
    public static  boolean startLoading;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        checkRuntimePermission();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            songs = new ArrayList<SongData>();
            metadataRetriever = new MediaMetadataRetriever();
            final int memory = (int) (Runtime.getRuntime().maxMemory()/1024);
            final int cache = memory/8;
           /* mMemoryCache = new LruCache<String, Bitmap>(cache){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return  value.getByteCount()/1024;
                }
            };*/
            songList = (RecyclerView) findViewById(R.id.recylcerview);
            getAllSongs();
            SonDataAdapter adapter = new SonDataAdapter(this, songs);
            songList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            songList.setAdapter(adapter);

        }
    }

    private void getAllSongs() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int ID = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                do {
                    long id = cursor.getInt(ID);
                    long albumId = cursor.getLong(albumIdIndex);
                    String title = cursor.getString(titleColumn);
                    String path = cursor.getString(dataColumn);
                    songs.add(new SongData( title, path, id, albumId));

                } while (cursor.moveToNext());
            }
        }
    }
   /* public static void setBitmapIntoCache(String key , Bitmap bitmap) {
        if(getBitmapFromCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }*/

  /*public static Bitmap getBitmapFromCache(String key) {
        return mMemoryCache.get(key);
    }*/

    public void checkRuntimePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_RESULT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_RESULT:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else
                    checkRuntimePermission();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
