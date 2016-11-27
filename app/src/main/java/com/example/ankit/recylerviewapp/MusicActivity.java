package com.example.ankit.recylerviewapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MusicActivity extends Activity {
    private int TIMER=0;
    private Button b1;
    private boolean check=false;
    private BasicFragment basicFragment;
    MediaPlayer mediaPlayer=new MediaPlayer();
    int currentpos;
    SeekBar seekBar;
    int hour ;
    int min;
    int sec;
    int totalTime;
    TextView time1,time2;
    String setText;
    ArrayList<SongData> son;
    SongData curr;
    Uri uri;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String calu="";
            String second = "";
            totalTime = mediaPlayer.getCurrentPosition();
            hour =  (totalTime / (1000 * 60 * 60));
            min = (totalTime % (1000 * 60 * 60)) / (1000 * 60);
            sec = ((totalTime % (1000 * 60 * 60)) % (1000 * 60) / 1000);
            if(hour > 0) {
                calu = hour +":";
            }
            if(sec<10) {
                second = "0"+sec;
            }
            else {
                second += sec;
            }
            calu = calu + min + ":" + second;
            time1.setText(calu);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
        initliazation();
       FragmentManager manager = getFragmentManager();
       basicFragment = (BasicFragment)manager.findFragmentByTag("data");
        try {
            Log.d("MY TAG",""+basicFragment.toString()+"");
        }catch (Exception e) {

        }

        if(basicFragment == null) {
            basicFragment = new BasicFragment();
            manager.beginTransaction().add(basicFragment,"data");
            Intent intent = getIntent();
            String path = intent.getStringExtra("path");
            Log.d("MY TAG", "" + mediaPlayer.isPlaying());
            uri = Uri.fromFile(new File(path));
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            basicFragment.setMediaPlayer(mediaPlayer);
        }
        else {
            mediaPlayer = basicFragment.getMediaPlayer();
        }
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
      /*  Bundle b = getIntent().getExtras();
        mediaPlayer = (MediaPlayer) b.getSerializable("Current");
        int pos = b.getInt("pos",0);
        curr = son.get(pos);
        // ArrayList<SongData> songDatas = (ArrayList<SongData>) getIntent().getSerializableExtra("SongList");

        Log.d("My Tag", ""+curr.getTitle());
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            setText = cal(mediaPlayer.getDuration());
            time2.setText(setText);
            seek();
            b1.setBackgroundResource(R.drawable.ic_media_pause);
            check = true;
        }*/
        /*String arr[] = {"Airtel_song", "Jabra_fan","Har Kisi ko nahi milta","mein Agar samnie","Ankhon ke paane",
                "Aaj raat ka scene","Dhik chika","Lolipop lagu lu","wo jaha rha",
        "bhiar", "loliii","lakjfklajlkfjakl","afikrlfkajfkafaijasjfi;oas"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.text_layout,son);*/
      /*   b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check) {
                    if(mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(currentpos);
                    }

                    mediaPlayer.start();


                    check=true;
                    b1.setBackgroundResource(R.drawable.ic_media_pause);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp){
                            b1.setBackgroundResource(R.drawable.ic_media_play);
                            check=false;
                        }
                    });
                }
                else {
                    if(mediaPlayer.isPlaying()) {
                        currentpos = mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();

                    }
                    else {
                        mediaPlayer.stop();
                    }
                    check = false;
                    b1.setBackgroundResource(R.drawable.ic_media_play);
                }
            }
        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basicFragment.setMediaPlayer(mediaPlayer);
    }

    private void initliazation() {
        b1 = (Button) findViewById(R.id.play_button);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
    }
    public void seek() {
        Thread thread;
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        thread = new Thread() {
            @Override
            public void run() {

                while (mediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.sendEmptyMessage(TIMER);
                }
            }
        };
        thread.start();
    }
    String cal(int i) {
        String calu="";
        String second = "";
        totalTime = i;
        hour =  (totalTime / (1000 * 60 * 60));
        min = (totalTime % (1000 * 60 * 60)) / (1000 * 60);
        sec =  ((totalTime % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if(hour > 0) {
            calu = hour +":";
        }
        if(sec<10) {
            second = "0"+sec;
        }
        else {
            second += sec;
        }
        calu = calu + min + ":" + second;
        return calu;

    }


}
