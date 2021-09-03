package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class LyricsActivity extends AppCompatActivity {
//    String path = "/storage/self/primary/Android/data/com.example.aarti_sangrah/files/";
    String path ;
    TextView subtitletv;
    String filename,fileURL;
    StorageReference mref;
//    File file;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        path = getFilesDir().getAbsolutePath();
        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");
        fileURL = intent.getStringExtra("fileURL");
        Log.d("filename",filename);
        Log.d("fileURL",fileURL);
        Log.d("filoename lyrics", filename);
        File file = new File(path + filename);
        Log.d("path",file.getAbsolutePath());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        subtitletv = (TextView)findViewById(R.id.lyrics_txtView);
        if(file.exists()){
            Log.d("Inside If","Lyrics Activity");
            setLyrics(file);
        }
        else{
            Toast.makeText(LyricsActivity.this,"Downloading please wait.",Toast.LENGTH_SHORT).show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference()
                    .child("lyrics")
                    .child(filename);

            storageRef.getBytes(1024*1024)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        //gs://aartisangrah-ee3b7.appspot.com/lyrics/sukhkarta_dukhharta.txt
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.d("LyricsActivity","in:onSuccess");
                            try {
                                writeToFile(bytes);
                            } catch (IOException e) {
                                Log.d("LyricsActivity","in:onSuccess:catch");
                                e.printStackTrace();
                            }
                        }
                    });

        }

    }

    private void setLyrics(File file) {
        try {
            Log.d("Inside try","Lyrics Activity");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            String line = null;
            try {
                while((line = br.readLine()) != null)
                {
                    subtitletv.append(line);
                    subtitletv.append("\n");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeToFile(byte[] array) throws IOException {
        Log.d("LyricsActivity","in:writeToFile");
        try
        {
            File mFile = new File(path);
            File file = new File(path+filename);

            if (!file.exists()) {
                mFile.mkdirs();
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path+filename);
            stream.write(array);
            setLyrics(file);
        } catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }
}