package com.example.jjapstagram_java.myinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjapstagram_java.databinding.ItemPostThumbBinding;

import java.util.Vector;

public class PostThumbAdapter extends RecyclerView.Adapter<PostThumbAdapter.PostThumbViewHolder> {

    private Vector<Integer> postThumbs = new Vector<>();

    private Context context;

    public PostThumbAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PostThumbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostThumbBinding binding = ItemPostThumbBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PostThumbViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostThumbViewHolder holder, int position) {
        ItemPostThumbBinding binding = holder.binding;


    }

    @Override
    public int getItemCount() {
        return postThumbs.size();
    }

    public void update(Vector<Integer> postThumbs) {
        this.postThumbs = postThumbs;
        notifyDataSetChanged();
    }

    class PostThumbViewHolder extends RecyclerView.ViewHolder {

        private ItemPostThumbBinding binding;

        public PostThumbViewHolder(ItemPostThumbBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
