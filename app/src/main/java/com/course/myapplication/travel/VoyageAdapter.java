package com.course.myapplication.travel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.course.myapplication.R;

import java.util.List;

import static com.course.myapplication.travel.VoyageRecyclerActivity.ITEM_COUNT;

public class VoyageAdapter extends RecyclerView.Adapter<VoyageViewHolder> {
    public VoyageAdapter(ItemListener listener, List<Voyage> source) {
        mListener = listener;
        mSource = source;
    }

    List<Voyage> mSource;
    ItemListener mListener;

    @NonNull
    @Override
    public VoyageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.voyage_item, viewGroup, false);
        return new VoyageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VoyageViewHolder voyageViewHolder, int i) {
        voyageViewHolder.setIndex(i);
        voyageViewHolder.setVoyage(mSource.get(i));
        voyageViewHolder.setItemListener(mListener);
        voyageViewHolder.getImageView().setImageResource(VoyageRecyclerActivity.getImageResource(i));

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }
}
