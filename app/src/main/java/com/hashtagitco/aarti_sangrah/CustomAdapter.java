package com.hashtagitco.aarti_sangrah;


import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hashtagitco.aarti_sangrah.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    Context mContext;

    private ArrayList<String> mEnglishNames = new ArrayList<>();

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mBackgroundUrls = new ArrayList<>();
    private ArrayList<String> filename = new ArrayList<>();
    private ArrayList<String> fileURL = new ArrayList<>();
    private ArrayList<String> songURL = new ArrayList<>();
    List<String> englishSongListAll = new ArrayList<>();



    public CustomAdapter(Context mContext, ArrayList<String> mEnglishNames, ArrayList<String> mImageUrls, ArrayList<String> mBackgroundURLs, ArrayList<String> filename,ArrayList<String> fileURL,ArrayList<String> songURL) {
        this.mContext = mContext;
        this.mEnglishNames = mEnglishNames;
        this.mBackgroundUrls = mBackgroundURLs;
        this.mImageUrls = mImageUrls;
        this.englishSongListAll = new ArrayList<>(mEnglishNames);
        this.filename = filename;
        this.fileURL = fileURL;
        this.songURL = songURL;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(englishSongListAll);
            }
            else{
                for(String songName : englishSongListAll){
                    if(songName.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(songName);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;


            return filterResults;
        }
        //run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mEnglishNames.clear();
            mEnglishNames.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mEnglishName;
        ImageView mThumbnail;

        //        private final Button button;
        public ViewHolder(View view) {
            super(view);
//            mHindiName = view.findViewById(R.id.hindi_name);
            mEnglishName = view.findViewById(R.id.english_name);
            mEnglishName.setMovementMethod(new ScrollingMovementMethod());
            mThumbnail = view.findViewById(R.id.aarti_image_view);
            // Define click listener for the ViewHolder's View
//            view.setOnClickListener(new View.OnClickListener() {
//                public Context mContext;
//
//                @Override
//                public void onClick(View v) {
//                    mContext.startActivity(new Intent(mContext,SongActivity.class));
//                }
//            });

        }

//        public TextView getHindiTextView() {
//            return mHindiName;
//        }
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
//        viewHolder.getHindiTextView().setText(mHindiNames.get(position));
        viewHolder.getEnglishTextView().setText(mEnglishNames.get(position));

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .placeholder(R.drawable.loading_thumbnail)
                .priority(Priority.IMMEDIATE)
                .encodeFormat(Bitmap.CompressFormat.PNG)
                .format(DecodeFormat.DEFAULT);

        Glide.with(mContext)
                .applyDefaultRequestOptions(requestOptions)
                .load(mImageUrls.get(position))
                        .error(Glide.with(mContext)
                                .load(R.drawable.loading_thumbnail))
                .into(viewHolder.getImageView());

//        Glide.with(mContext).load(mImageUrls.get(position)).into(viewHolder.getImageView());
//        Picasso.get().load(mImageUrls.get(position)).into(viewHolder.getImageView());
        viewHolder.getEnglishTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SongActivity.class);
                intent.putExtra("bg_image",mBackgroundUrls.get(position));
                intent.putExtra("filename",filename.get(position));
                intent.putExtra("fileURL",fileURL.get(position));
                intent.putExtra("songURL",songURL.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
//
    }


    @Override
    public int getItemCount() {
        return mEnglishNames.size();
    }
}
