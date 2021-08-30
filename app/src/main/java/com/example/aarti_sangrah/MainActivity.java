package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rbddevs.splashy.Splashy;

public class MainActivity extends AppCompatActivity {
    CardView mCardView;
    RecyclerView mRecyclerView;
    String[] testString = {"Audio 1","Audio 2","Audio 3","Audio 4","Audio 5","Audio 6","Audio 7","Audio 8","Audio 9","Audio 10","Audio 11",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardView = findViewById(R.id.displayCard);
        mCardView.setBackgroundResource(R.drawable.recycler_view_bg);

         new Splashy(this)  // For JAVA : new Splashy(this)
                 .setLogo(R.drawable.aarti_logo)
                 .setTitle("Aarti Sangrah")
                 .setTitleColor("#FFFFFF")
                 .setSubTitle("सर्वेषां मङ्गलम् भवतु।")
                 .setSubTitleColor("#FFFFFF")
                 .setProgressColor(R.color.white)
                 .setBackgroundResource(R.drawable.mohanjodado)
                 .setFullScreen(true)
                 .show();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.audioRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        CustomAdapter mAdapter = new CustomAdapter(getApplicationContext(),testString);
        mRecyclerView.setAdapter(mAdapter);


    }
}