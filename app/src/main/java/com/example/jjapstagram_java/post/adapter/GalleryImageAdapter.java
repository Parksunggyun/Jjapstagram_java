package com.example.jjapstagram_java.post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ItemGalleryImageBinding;
import com.example.jjapstagram_java.post.Thumbnails;

import java.io.File;
import java.util.Vector;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder> {

    private Vector<Thumbnails> thumbnails = new Vector<>();
    private Vector<Thumbnails> selectedThumbnails = new Vector<>();
    private Context context;
    private int height = 0;
    private boolean multiSelectMode = false;

    public GalleryImageAdapter(Context context) {
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

        if (height != 0) {
            binding.galleryCardView.getLayoutParams().height = height;
        }

        Thumbnails thumbnail = thumbnails.get(position);
        //binding.galleryImgView.setImageBitmap(thumbnail.getImage());
        Glide.with(context).load(new File(thumbnail.getUri().getPath())).override(height).into(binding.galleryImgView);
        binding.translucentView.setVisibility(thumbnail.isSelect() ? View.VISIBLE : View.GONE);
        if (multiSelectMode) {
            binding.multiSelectLayout.setVisibility(View.VISIBLE);
            int selectedPosition = thumbnail.getSelectPosition();
            if (selectedPosition != -1) {
                binding.multiSelectLayout.setBackgroundResource(R.drawable.border_image_sel);
                binding.selectedCntTxtView.setText(String.valueOf(selectedPosition));
            } else {
                binding.multiSelectLayout.setBackgroundResource(R.drawable.border_image_desel);
                binding.selectedCntTxtView.setText(null);
            }
        } else {
            binding.multiSelectLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return thumbnails.size();
    }

    public void update(int height, Thumbnails thumbnail) {
        this.height = height;
        this.thumbnails.add(thumbnails.size(), thumbnail);
        notifyItemChanged(thumbnails.size());
    }

    public void init() {
        thumbnails.removeAllElements();
        thumbnails = null;
        thumbnails = new Vector<>();
        notifyDataSetChanged();
    }

    public Thumbnails getThumbInfo(int pos) {
        return thumbnails.get(pos);
    }

    public void setSelected(int pos, int pos2) {
        thumbnails.get(pos).setSelect(true);
        if(pos2 != -1) {
            thumbnails.get(pos2).setSelect(false);
            notifyItemChanged(pos2);
        } else {
            thumbnails.get(0).setSelect(false);
            notifyItemChanged(0);
        }
        notifyItemChanged(pos);
    }

    public void setMultiSelectMode(boolean multiSelectMode, int pos, int cnt) {
        this.multiSelectMode = multiSelectMode;
        thumbnails.get(pos).setSelectPosition(cnt);
        notifyDataSetChanged();
    }

    public void setSingSelectMode(boolean multiSelectMode) {
        this.multiSelectMode = multiSelectMode;
        for (int i = 0; i < thumbnails.size(); i++) {
            thumbnails.get(i).setSelectPosition(-1);
            thumbnails.get(i).setSelect(i == 0);
        }
        notifyDataSetChanged();
    }

    public void modifySelectedPosition(int cnt) {
        int currPos = thumbnails.get(cnt).getSelectPosition();
        for (int i = 0; i < thumbnails.size(); i++) {
            int selectedPosition = thumbnails.get(i).getSelectPosition();
            if(selectedPosition != -1 && selectedPosition > currPos) {
                thumbnails.get(i).setSelectPosition(selectedPosition - 1);
                notifyItemChanged(i);
            }
        }
    }

    class GalleryImageViewHolder extends RecyclerView.ViewHolder {

        ItemGalleryImageBinding binding;

        GalleryImageViewHolder(ItemGalleryImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

