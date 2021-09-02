package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

import java.io.IOException;
import java.util.Set;

public class SongActivity extends AppCompatActivity {

    MediaPlayer simpleExoPlayer;
//    SimpleExoPlayer simpleExoPlayer;
    boolean isPlaying;
    ImageView bellImageView1,bellImageView2,mBackgroundImageView;
    String mBackgroundURL,filename,fileURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        isPlaying = false;
        mBackgroundImageView = findViewById(R.id.song_bg_imageView);
        Intent intent = getIntent();
        mBackgroundURL = intent.getStringExtra("bg_image");
        filename = intent.getStringExtra("filename");
        fileURL = intent.getStringExtra("fileURL");
        Log.d("filename SongActivity",filename);
        Log.d("fileURL SongActivity",fileURL);
        Glide.with(getApplicationContext()).load(mBackgroundURL).into(mBackgroundImageView);

    }

    public void bellPressed1(View view) {
        bellImageView1 = (ImageView)findViewById(R.id.song_bell_btn1);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        bellImageView1.startAnimation(aniRotate);
    }

    public void bellPressed2(View view) {
        bellImageView2 = (ImageView)findViewById(R.id.song_bell_btn2);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        bellImageView2.startAnimation(aniRotate);
    }

    public void play_btn(View view) throws IOException {
        ImageView playBtn = findViewById(R.id.song_play_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        if(!isPlaying){
            playBtn.setImageResource(R.drawable.ic_pause);
            SetupPlayer();
//            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.start();
            isPlaying = true;
        }
        else if(isPlaying){
            playBtn.setImageResource(R.drawable.ic_play);
//            simpleExoPlayer.stop();
//            simpleExoPlayer.setPlayWhenReady(false);
//            simpleExoPlayer.stop();
            simpleExoPlayer.pause();

//            simpleExoPlayer.seekTo(0);
            isPlaying = false;
//            simpleExoPlayer.release();

        }
        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
//        myAnim.setInterpolator(interpolator);

        playBtn.startAnimation(myAnim);
    }

    public void replayPressed(View view) throws IOException {
        ImageView replayBtn = findViewById(R.id.song_replay_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        replayBtn.startAnimation(myAnim);
        simpleExoPlayer.stop();
        SetupPlayer();
//        simpleExoPlayer.setPlayWhenReady(true);
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
    }
//    *************** EXO PLAYER******************************************************

    private void SetupPlayer() throws IOException {
//        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(
//                this,null,DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
//        );
//        TrackSelector trackSelector = new DefaultTrackSelector();
//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(),renderersFactory,trackSelector);
//
//        String userAgent = Util.getUserAgent(this,"Play Audio");
//        ExtractorMediaSource mediaSource = new ExtractorMediaSource(
//                Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"),
//                new DefaultDataSourceFactory(this,userAgent),
//                new DefaultExtractorsFactory(),
//                null,
//                null
//        );
//        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer = new MediaPlayer();
        simpleExoPlayer = MediaPlayer.create(this,Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"));
//        simpleExoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        simpleExoPlayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3");


    }

    private void release_player(){
//        simpleExoPlayer.release();
    }
    @Override
    protected void onDestroy(){
//        simpleExoPlayer.release();
        super.onDestroy();
    }

    @Override
    protected void onPause(){
//        simpleExoPlayer.release();
        super.onPause();
    }
}