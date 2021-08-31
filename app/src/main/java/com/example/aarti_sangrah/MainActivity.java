package com.example.aarti_sangrah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbddevs.splashy.Splashy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView mCardView;
    RecyclerView mRecyclerView;
//    ArrayList<String> mHindiNames = new ArrayList<>();
    ArrayList<String> mEnglishNames = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("aarti");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mHindiNames.clear();
                mEnglishNames.clear();
                mImageUrls.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
//                    mHindiNames.add(ds.child("hindi_name").getValue().toString());
                    Log.d("English NAMES",ds.child("english_name").getValue().toString());
                    mEnglishNames.add(ds.child("english_name").getValue().toString());
                    mImageUrls.add(ds.child("image_url").getValue().toString());
                }
//                Log.d("English NAMES",mEnglishNames.toString());
                setRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity()","Failed" + " : " + error.toString());
            }
        });


//        for(int i = 0; i<15; i++){
//            mHindiNames.add("hindi name" + String.valueOf(i));
//            mEnglishNames.add("english name" + String.valueOf(i));
//        }
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

    public void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.audioRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CustomAdapter(getApplicationContext(),mEnglishNames,mImageUrls);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}