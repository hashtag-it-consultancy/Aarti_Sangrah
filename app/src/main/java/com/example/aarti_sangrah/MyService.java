package com.example.aarti_sangrah;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    public class LocalBinder extends Binder {

        private final MyService mLocalService;

        public LocalBinder(final MyService service) {
            mLocalService = service;
        }

        MyService getService() {
            return MyService.this;
        }
    }


    private MediaPlayer mMediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"));
        mMediaPlayer.setLooping(false);
    }

    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        mMediaPlayer.start();
    }

    public void onDestroy(){
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        mMediaPlayer.stop();
    }

    public void onPause(){
        mMediaPlayer.pause();
    }

    public void onPlay(){
        mMediaPlayer.release();
    }
}