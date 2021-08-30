package com.example.aarti_sangrah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.rbddevs.splashy.Splashy;

public class MainActivity extends AppCompatActivity {
    CardView mCardView;
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

    }
}