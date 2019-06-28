package com.example.jjapstagram_java.favorite.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentMyPostedBinding;

public class MyPostedFragment extends BaseFragment {

    private FragmentMyPostedBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_posted, container, false);
        return binding.getRoot();
    }

}
