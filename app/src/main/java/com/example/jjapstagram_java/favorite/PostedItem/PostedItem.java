package com.example.jjapstagram_java.favorite.PostedItem;

import java.util.Vector;

public class PostedItem {

    private int whoImg;
    private Vector<String> whosNames;
    private int postedThunmbImg;

    public PostedItem(int whoImg, Vector<String> whosNames, int postedThunmbImg) {
        this.whoImg = whoImg;
        this.whosNames = whosNames;
        this.postedThunmbImg = postedThunmbImg;
    }

    public int getWhoImg() {
        return whoImg;
    }

    public Vector<String> getWhosNames() {
        return whosNames;
    }

    public int getPostedThunmbImg() {
        return postedThunmbImg;
    }
}
