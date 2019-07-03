package com.example.jjapstagram_java.post.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PostFragmentAdapter extends FragmentStatePagerAdapter {

    private int width;

    private final PostGalleryFragment mPostGalleryFragment = new PostGalleryFragment();
    private final PostPhotoFragment mPostPhotoFragment = new PostPhotoFragment();
    private final PostVideoFragment mPostVideoFragment = new PostVideoFragment();

    public PostFragmentAdapter(FragmentManager fm, int width) {
        super(fm);
        this.width = width;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putInt("height", width);
                mPostGalleryFragment.setArguments(bundle);
                return mPostGalleryFragment;
            case 1:
                return mPostPhotoFragment;
            case 2:
                return mPostVideoFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
