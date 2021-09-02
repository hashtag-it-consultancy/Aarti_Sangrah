package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultRenderersFactory;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.api.Api;

import java.io.IOException;
import java.util.Set;

public class SongActivity extends AppCompatActivity {

//    MediaPlayer simpleExoPlayer;
//    SimpleExoPlayer simpleExoPlayer;
     Intent serviceIntent;
    boolean isPlaying;
    ImageView bellImageView1,bellImageView2,mBackgroundImageView;
    String mBackgroundURL,filename,fileURL;
    int active = 0;
    MyService myService;

    public ServiceConnection myConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        serviceIntent = new Intent(getApplicationContext(),MyService.class);



        isPlaying = false;
        mBackgroundImageView = findViewById(R.id.song_bg_imageView);
        Intent intent = getIntent();
        mBackgroundURL = intent.getStringExtra("bg_image");
        filename = intent.getStringExtra("filename");
        fileURL = intent.getStringExtra("fileURL");
        Log.d("filename SongActivity",filename);
        Log.d("fileURL SongActivity",fileURL);
        Glide.with(getApplicationContext()).load(mBackgroundURL).into(mBackgroundImageView);

        serviceIntent = new Intent(getApplicationContext(),MyService.class);

    }
//    ******************************************************************************

//    ******************************************************************************



    public void bellPressed1(View view) {
        bellImageView1 = (ImageView)findViewById(R.id.song_bell_btn1);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        bellImageView1.startAnimation(aniRotate);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.ringbell);
        mp.start();
    }

    public void bellPressed2(View view) {
        bellImageView2 = (ImageView)findViewById(R.id.song_bell_btn2);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        bellImageView2.startAnimation(aniRotate);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.ringbell);
        mp.start();
    }

    public void play_btn(View view) throws IOException {
        ImageView playBtn = findViewById(R.id.song_play_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        if(active == 0){
            startService(serviceIntent);

             myConnection = new ServiceConnection() {
                @Override
                public void onServiceDisconnected(ComponentName name) {
//                    Toast.makeText(Api.Client.this, "Service is disconnected", 1000).show();
//                    mBounded = false;
//                    mServer = null;
                    Log.d("Service","Disconnected");
                }

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
//                    Toast.makeText(Client.this, "Service is connected", 1000).show();
//                    mBounded = true;
                    Log.d("Service","Connected");
                    MyService.LocalBinder mLocalBinder = (MyService.LocalBinder)service;
                    myService = mLocalBinder.getService();
                }
             };

            bindService(serviceIntent, myConnection, BIND_AUTO_CREATE);

            playBtn.setImageResource(R.drawable.ic_pause);
            active = 1;
        }else{
//            stopService(serviceIntent);

            myService.onPause();
            playBtn.setImageResource(R.drawable.ic_play);
            active = 0;
        }

        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
//        myAnim.setInterpolator(interpolator);

        playBtn.startAnimation(myAnim);
    };

    public void replayPressed(View view) throws IOException {
        ImageView replayBtn = findViewById(R.id.song_replay_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        replayBtn.startAnimation(myAnim);
        stopService(new Intent(getApplicationContext(),MyService.class));
        startService(new Intent(getApplicationContext(),MyService.class));


    }

    public void lyricsPressed(View view) {
        ImageView lyricsBtn = findViewById(R.id.song_lyrics_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        lyricsBtn.startAnimation(myAnim);
        Intent intent = new Intent(SongActivity.this,LyricsActivity.class);
        intent.putExtra("filename",filename);
        intent.putExtra("fileURL",fileURL);
        startActivity(intent);
    }

    public void shankhPressed(View view) {
        ImageView shankhBtn = findViewById(R.id.song_shankh_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        shankhBtn.startAnimation(myAnim);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shankh);
        mp.start();
    }
//    *************** EXO PLAYER******************************************************




}