package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class LyricsActivity extends AppCompatActivity {
    String path = "/storage/self/primary/Android/data/com.example.aarti_sangrah/files/";
    TextView subtitletv;
    String filename,fileURL;
    StorageReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");
        fileURL = intent.getStringExtra("fileURL");
        Log.d("filename",filename);
        Log.d("fileURL",fileURL);
        Log.d("filoename lyrics", filename);
        File file = new File(path + filename);
        Log.d("path",file.getAbsolutePath());

        subtitletv = (TextView)findViewById(R.id.lyrics_txtView);
        if(file.exists()){
            Log.d("Inside If","Lyrics Activity");
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
        else{
            Toast.makeText(LyricsActivity.this,"No Lyrics File",Toast.LENGTH_SHORT).show();
            mref = FirebaseStorage.getInstance().getReference();
            StorageReference filepath = mref.child("lyrics").child(filename);
            InputStream inputStream = null;
            File file1 = null;
            FileOutputStream fileOutputStream = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                try {
                    file1 = new File(path + filename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}