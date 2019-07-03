package com.example.jjapstagram_java.post;

import android.net.Uri;

public class Thumbnails {

    private String title;
    private Uri uri;
    private int selectPosition;
    private boolean select;

    public Thumbnails(String title, Uri uri) {
        this.title = title;
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
