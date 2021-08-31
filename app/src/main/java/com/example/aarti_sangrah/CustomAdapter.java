package com.example.aarti_sangrah;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<String> mHindiNames = new ArrayList<>();
    private ArrayList<String> mEnglishNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public CustomAdapter(Context mContext,ArrayList<String> mHindiNames, ArrayList<String> mEnglishNames, ArrayList<String> mImageUrls) {
        this.mContext = mContext;
        this.mHindiNames = mHindiNames;
        this.mEnglishNames = mEnglishNames;
        this.mImageUrls = mImageUrls;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mHindiName,mEnglishName;
        ImageView mThumbnail;

        //        private final Button button;
        public ViewHolder(View view) {
            super(view);
            mHindiName = view.findViewById(R.id.hindi_name);
            mEnglishName = view.findViewById(R.id.english_name);
            mThumbnail = view.findViewById(R.id.aarti_image_view);
            // Define click listener for the ViewHolder's View

        }

        public TextView getHindiTextView() {
            return mHindiName;
        }
        public TextView getEnglishTextView() {
            return mEnglishName;
        }
        public ImageView getImageView() {
            return mThumbnail;
        }

    }






    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.audio_card, viewGroup, false);

        return new ViewHolder(view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getHindiTextView().setText(mHindiNames.get(position));
        viewHolder.getEnglishTextView().setText(mEnglishNames.get(position));
        Glide.with(mContext).load(mImageUrls.get(position)).into(viewHolder.getImageView());

    }


    @Override
    public int getItemCount() {
        return mHindiNames.size();
    }
}
