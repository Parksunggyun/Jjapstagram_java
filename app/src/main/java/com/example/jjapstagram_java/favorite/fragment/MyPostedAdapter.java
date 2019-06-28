package com.example.jjapstagram_java.favorite.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjapstagram_java.databinding.ItemMyPostPeriodBinding;
import com.example.jjapstagram_java.databinding.ItemMyPostPostedBinding;
import com.example.jjapstagram_java.favorite.PostedItem.PostedItem;

import java.util.Vector;

public class MyPostedAdapter extends RecyclerView.Adapter {

    private final int PERIOD = 1000;
    private final int POSTED = 2000;

    private Vector<PostedItem> thisWeekPostedItems = new Vector<>();
    private Vector<PostedItem> postedItems = new Vector<>();

    private Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == PERIOD) {
            ItemMyPostPeriodBinding binding = ItemMyPostPeriodBinding.inflate(LayoutInflater.from(context), parent, false);
            return new PostPeriodViewHolder(binding);
        } else {
            ItemMyPostPostedBinding binding = ItemMyPostPostedBinding.inflate(LayoutInflater.from(context), parent, false);
            return new MyPostedViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostPeriodViewHolder) {
            ItemMyPostPeriodBinding binding = ((PostPeriodViewHolder) holder).binding;
                binding.periodTxtView.setText(position == 0 ? "이번 주" : "이번 달");
        } else {
            ItemMyPostPostedBinding binding = ((MyPostedViewHolder) holder).binding;

        }
    }

    @Override
    public int getItemCount() {
        return postedItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return PERIOD;
        } else if(position <= thisWeekPostedItems.size()) {
            return POSTED;
        } else if(position == thisWeekPostedItems.size() + 1) {
            return PERIOD;
        } else {
            return POSTED;
        }
    }

    public class MyPostedViewHolder extends RecyclerView.ViewHolder {

        ItemMyPostPostedBinding binding;

        MyPostedViewHolder(ItemMyPostPostedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public class PostPeriodViewHolder extends RecyclerView.ViewHolder {

        ItemMyPostPeriodBinding binding;

        PostPeriodViewHolder(ItemMyPostPeriodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
