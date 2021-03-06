package com.hashtagitco.aarti_sangrah;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    SongActivity mSongActivity;
    String URL;

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

    }

    public void onStart(Intent intent, int startid){
        URL = intent.getStringExtra("songURL");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(URL));
        mMediaPlayer.setLooping(false);
        Log.d("SONGDATA",URL);
        mMediaPlayer.start();
    }

    public void onDestroy(){
        mMediaPlayer.stop();
    }

    public void onPause(){
        mMediaPlayer.pause();
    }

    public void onPlay(){
        mMediaPlayer.release();
    }
}
