package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SongActivity extends AppCompatActivity {
    ImageView bellImageView1,bellImageView2,mBackgroundImageView;
    String mBackgroundURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        mBackgroundImageView = findViewById(R.id.song_bg_imageView);
        Intent intent = getIntent();
        mBackgroundURL = intent.getStringExtra("bg_image");
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

    public void play_btn(View view) {
        ImageView playBtn = findViewById(R.id.song_play_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
//        myAnim.setInterpolator(interpolator);

        playBtn.startAnimation(myAnim);
    }

    public void replayPressed(View view) {
        ImageView replayBtn = findViewById(R.id.song_replay_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        replayBtn.startAnimation(myAnim);
    }

    public void lyricsPressed(View view) {
        ImageView lyricsBtn = findViewById(R.id.song_lyrics_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        lyricsBtn.startAnimation(myAnim);
    }

    public void shankhPressed(View view) {
        ImageView shankhBtn = findViewById(R.id.song_shankh_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        shankhBtn.startAnimation(myAnim);
    }
}