package com.example.jjapstagram_java.post;

import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class PostTabListener implements TabLayout.OnTabSelectedListener {

    ViewPager viewPager;
    Post post;
    int position;

    public PostTabListener(ViewPager viewPager, Post post, int position) {
        this.viewPager = viewPager;
        this.post = post;
        this.position = position;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e("onTabSelected", "called");
        position = tab.getPosition();
        post.setPosition(position);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
