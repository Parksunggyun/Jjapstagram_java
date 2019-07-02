package com.example.jjapstagram_java.post;

import android.graphics.Bitmap;
import android.net.Uri;

public class Thumbnails {

    private String title;
    private Bitmap image;
    private Uri uri;
    private int selectPosition;
    private boolean select;

    public Thumbnails(String title, Bitmap image, Uri uri) {
        this.title = title;
        this.image = image;
        this.uri = uri;
        select = false;
        selectPosition = -1;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }

    public Uri getUri() {
        return uri;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public boolean isSelect() {
        return select;
    }
}
