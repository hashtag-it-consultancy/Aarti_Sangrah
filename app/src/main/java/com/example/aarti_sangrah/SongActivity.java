package com.example.aarti_sangrah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.api.Api;

import java.io.IOException;
import java.util.Set;

public class SongActivity extends AppCompatActivity {

//    MediaPlayer simpleExoPlayer;
//    SimpleExoPlayer simpleExoPlayer;
     Intent serviceIntent;
    boolean isPlaying;
    ImageView bellImageView1,bellImageView2,mBackgroundImageView,playBtn;
    String mBackgroundURL,filename,fileURL,songURL;
    int active = 0;
    MyService myService;
    private InterstitialAd mInterstitialAd;
    private Boolean access = false;
    int requestCode = 1;



    public ServiceConnection myConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        playBtn = findViewById(R.id.song_play_btn);

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-6518391481638658/9357443940", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SongActivity.this);

                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        },4000);

        isPlaying = false;
        mBackgroundImageView = findViewById(R.id.song_bg_imageView);
        Intent intent = getIntent();
        mBackgroundURL = intent.getStringExtra("bg_image");
        filename = intent.getStringExtra("filename");
        fileURL = intent.getStringExtra("fileURL");
        songURL = intent.getStringExtra("songURL");
        serviceIntent = new Intent(getApplicationContext(),MyService.class);
        serviceIntent.putExtra("songURL",songURL);
        Log.d("filename SongActivity",filename);
        Log.d("fileURL SongActivity",fileURL);
        Log.d("songURL SongActivity",songURL);
        Glide.with(getApplicationContext()).load(mBackgroundURL).into(mBackgroundImageView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                access = true;
            } else{
                access = false;
                // Request Runtime Permissions
                ActivityCompat.requestPermissions(SongActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, requestCode);
//                    granted = 1;
                //////Toast.makeText(context, "Cannot Download, Please Grant Storage Permission!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            access = true;
        }



//        serviceIntent = new Intent(getApplicationContext(),MyService.class);

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

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        if(active == 0){
            startService(serviceIntent);


            playBtn.setImageResource(R.drawable.ic_pause);
            active = 1;
        }else{
            stopService(serviceIntent);

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
        stopService(serviceIntent);
        startService(serviceIntent);
        playBtn.setImageResource(R.drawable.ic_pause);
        active = 1;


    }

    public void lyricsPressed(View view) {




        ImageView lyricsBtn = findViewById(R.id.song_lyrics_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        lyricsBtn.startAnimation(myAnim);

        Intent intent = new Intent(SongActivity.this, LyricsActivity.class);

        intent.putExtra("filename", filename);
        intent.putExtra("fileURL", fileURL);
        startActivity(intent);


//        ImageView lyricsBtn = findViewById(R.id.song_lyrics_btn);
//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//        lyricsBtn.startAnimation(myAnim);
//        Intent intent = new Intent(SongActivity.this,LyricsActivity.class);
//        intent.putExtra("filename",filename);
//        intent.putExtra("fileURL",fileURL);
//        startActivity(intent);

    }

    public void shankhPressed(View view) {
        ImageView shankhBtn = findViewById(R.id.song_shankh_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        shankhBtn.startAnimation(myAnim);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shankh);
//        mp.stop();
        mp.start();
    }
//    *************** EXO PLAYER******************************************************


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        active = 0;
//        Toast.makeText(getApplicationContext(),"16. onDestroy()", Toast.LENGTH_SHORT).show();
    }

}