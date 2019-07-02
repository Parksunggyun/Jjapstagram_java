package com.example.jjapstagram_java.post.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjapstagram_java.databinding.ItemGalleryImageBinding;
import com.example.jjapstagram_java.post.Thumbnails;

import java.util.Vector;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder> {

    private Vector<Thumbnails> thumbnails = new Vector<>();
    private Context context;

    GalleryImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryImageBinding binding = ItemGalleryImageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new GalleryImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageViewHolder holder, int position) {
        ItemGalleryImageBinding binding = holder.binding;

        Thumbnails thumbnail = thumbnails.get(position);
        binding.galleryImgView.setImageBitmap(thumbnail.getImage());

    }

    @Override
    public int getItemCount() {
        return thumbnails.size();
    }

    public void update(int pos, Thumbnails thumbnail) {
        this.thumbnails.add(pos, thumbnail);
        notifyItemChanged(pos);
    }

    class GalleryImageViewHolder extends RecyclerView.ViewHolder {

        ItemGalleryImageBinding binding;

        GalleryImageViewHolder(ItemGalleryImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

