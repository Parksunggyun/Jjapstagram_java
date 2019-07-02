package com.example.jjapstagram_java.post.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PostFragmentAdapter extends FragmentStatePagerAdapter {

    public PostFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PostGalleryFragment();
            case 1:
                return new PostPhotoFragment();
            case 2:
                return new PostVideoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
