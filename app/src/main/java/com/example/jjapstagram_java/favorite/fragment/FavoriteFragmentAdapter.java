package com.example.jjapstagram_java.favorite.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FavoriteFragmentAdapter extends FragmentStatePagerAdapter {

    public FavoriteFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FollowingFragment();
            case 1:
                return new MyPostedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
