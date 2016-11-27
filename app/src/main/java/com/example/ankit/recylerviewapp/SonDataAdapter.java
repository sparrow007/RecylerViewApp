package com.example.ankit.recylerviewapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;



class SonDataAdapter extends RecyclerView.Adapter<SonDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<SongData> data;
    private Uri uri;
    private MediaPlayer mediaPlayer;
   private Context c;
private  String check;
    final public static Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albumart");
   SonDataAdapter(Context context, ArrayList<SongData> list) {
        inflater = LayoutInflater.from(context);
        data = list;
        c = context;
       mediaPlayer = new MediaPlayer();
    }

   public static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorker> bitmapRefernce;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorker bitmapWork) {
            super(res, bitmap);
            bitmapRefernce = new WeakReference<BitmapWorker>(bitmapWork);
        }

        public BitmapWorker getBitmapWorker() {
            return bitmapRefernce.get();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SongData currentSong = data.get(position);
        holder.textView.setText(currentSong.getTitle());
        Uri uri = ContentUris.withAppendedId(sArtworkUri,currentSong.getAlbumId());
        Glide.with(c).load(uri).override(50,50).centerCrop().into(holder.imageView);
        Log.d("MY TAG", ""+position);
        /*if(a != null) {
            Bitmap bitmap = loadImage(a, 50, 50);
            if (bitmap != null) {
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setBackgroundResource(android.R.drawable.ic_menu_edit);
            }
        }
        else {
            holder.imageView.setBackgroundResource(android.R.drawable.ic_menu_edit);
        }*/
      /*  String imageKey = String.valueOf(position);
        final Bitmap bitmap = MainActivity.getBitmapFromCache(imageKey);
        if(bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);*/

         //  loadImage(a, holder.imageView, position, currentSong.getPath());

     //  Glide.with(holder.imageView.getContext()).load(a).into(holder.imageView);
       // Picasso.with(holder.imageView.getContext()).load().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        TextView textView1;
        View view;
         ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image_menu_editor);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textView1 = (TextView) itemView.findViewById(R.id.textView1);
             Log.d("MY TAG", ""+mediaPlayer.isPlaying());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SongData currentSong = data.get(getAdapterPosition());
            if(!mediaPlayer.isPlaying()) {
                check = currentSong.getPath();
                uri = Uri.fromFile(new File(check));
                mediaPlayer = MediaPlayer.create(c, uri);
                mediaPlayer.start();
            }
          else {

                    if(check.equals(currentSong.getPath())) {

                    }
                else {
                        mediaPlayer.stop();
                        check=currentSong.getPath();
                        uri = Uri.fromFile(new File(currentSong.getPath()));
                        mediaPlayer = MediaPlayer.create(c, uri);
                        mediaPlayer.start();
                    }
            }
        }

    }

  /* public void loadImage(byte[] arr, ImageView imageView, long id, String s) {
           if(cancelPotentialWork(id,imageView)) {
               BitmapWorker bitmapWorker = new BitmapWorker(imageView, arr, id, s, c);
               AsyncDrawable drawable = new AsyncDrawable(imageView.getResources(),bit, bitmapWorker);
               imageView.setImageDrawable(drawable);
               bitmapWorker.execute(id);
           }

    }*/

    public static boolean cancelPotentialWork(long data, ImageView imageView) {
        final BitmapWorker bitmapWorker = SonDataAdapter.getBitmampWork(imageView);
        if (bitmapWorker != null) {
            long bitmapData = bitmapWorker.ID;
            if (bitmapData == 0 || bitmapWorker.ID != data) {
                bitmapWorker.cancel(true);
            } else
                return false;
        }
        return true;
    }


    public static BitmapWorker getBitmampWork(ImageView imageView) {
        final Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AsyncDrawable) {
            final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            return asyncDrawable.getBitmapWorker();
        }
        return null;
    }
}


