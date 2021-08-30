package com.example.aarti_sangrah;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] localDataSet, mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        //        private final Button button;
        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.demoTextView);
            // Define click listener for the ViewHolder's View

        }

        public TextView getTextView() {
            return mTextView;
        }

    }


    public CustomAdapter(Context context,String[] dataSet) {
        this.mDataset = dataSet;
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
        viewHolder.getTextView().setText(mDataset[position]);

    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
