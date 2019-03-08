package com.course.myapplication.travel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.course.myapplication.R;

public class VoyageViewHolder extends RecyclerView.ViewHolder implements ViewHandle {

    private ItemListener mItemListener;
    private Voyage mVoyage;

    private ImageView mImageView;
    private TextView mTripNameView;
    private TextView mDestinationView;
    private View mView;
    private int mIndex;


    public ItemListener getItemListener() {
        return mItemListener;
    }

    public void setItemListener(ItemListener itemListener) {
        mItemListener = itemListener;
        setHandlers();
        itemListener.addViewHandle(this);
    }

    private void setHandlers() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onClick(mView, mIndex);
            }
        });
    }


    public Voyage getVoyage() {
        return mVoyage;
    }

    public void setVoyage(Voyage voyage) {
        mVoyage = voyage;
        mTripNameView.setText(voyage.getTripName());
        mDestinationView.setText(voyage.getDestination());
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public TextView getDestinationView() {
        return mDestinationView;
    }

    public void setDestinationView(TextView descriptionView) {
        mDestinationView = descriptionView;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public VoyageViewHolder(@NonNull View itemView) {
        super(itemView);
        mTripNameView = itemView.findViewById(R.id.tripname_item_text);
        mDestinationView = itemView.findViewById(R.id.destination_item_text);
        mImageView = itemView.findViewById(R.id.recycler_image);
        mView = itemView;
    }

    @Override
    public void setParameters(Voyage voyage) {
        setVoyage(voyage);
    }
}
