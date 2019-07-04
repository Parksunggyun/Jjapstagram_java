package com.example.jjapstagram_java.post;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ThumbDecoration extends RecyclerView.ItemDecoration {

    private int padding = 3;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        outRect.set(padding, 0, padding, padding);

        if(pos / 4 == 0) {
            outRect.top = padding;
        }
        if(pos % 4 == 0) {
            outRect.left = 0;
        }
        if (pos % 4 == 3) {
            outRect.right = 0;
        }
    }
}
