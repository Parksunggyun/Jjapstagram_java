package com.example.jjapstagram_java.myinfo;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentMainMyinfoBinding;

public class MyInfoMainFragment extends BaseFragment {

    private static final String TAG = MyInfoMainFragment.class.getSimpleName();

    private FragmentMainMyinfoBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_myinfo, container, false);
        Log.e(TAG, "onCreateView");
        return binding.getRoot();
    }
}
