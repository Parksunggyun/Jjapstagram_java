package com.example.jjapstagram_java.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentMainFavoriteBinding;
import com.example.jjapstagram_java.favorite.fragment.FavoriteFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteMainFragment extends BaseFragment {

    private FragmentMainFavoriteBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_favorite, container, false);

        binding.favoriteViewPager.setAdapter(new FavoriteFragmentAdapter(getChildFragmentManager()));
        binding.favoriteViewPager.setOffscreenPageLimit(2);
        binding.favoriteTabLayout.addTab(binding.favoriteTabLayout.newTab().setText(getString(R.string.myInfo_following)), 0, true);
        binding.favoriteTabLayout.addTab(binding.favoriteTabLayout.newTab().setText(getString(R.string.my_post)), 1);

        binding.favoriteTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        binding.favoriteViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.favoriteTabLayout));

        return binding.getRoot();
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            binding.favoriteViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

}
