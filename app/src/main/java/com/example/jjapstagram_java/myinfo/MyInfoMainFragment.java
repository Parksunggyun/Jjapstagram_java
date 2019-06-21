package com.example.jjapstagram_java.myinfo;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.Jjapplication;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentMainMyinfoBinding;
import com.example.jjapstagram_java.util.Logcat;
import com.google.firebase.auth.FirebaseUser;

import java.util.Vector;

public class MyInfoMainFragment extends BaseFragment {

    private static final String TAG = MyInfoMainFragment.class.getSimpleName();

    private FragmentMainMyinfoBinding binding;
    private FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_myinfo, container, false);
        Log.e(TAG, "onCreateView");
        mUser = Jjapplication.mUser;
        assert mUser != null;
        binding.userNameTxtView.setText(mUser.getDisplayName());
        binding.myInfoPostCntTxtView.setText(("5\n" + getString(R.string.myInfo_post)));
        binding.myInfoFollowerCntTxtView.setText(("63\n" + getString(R.string.myInfo_follower)));
        binding.myInfoFollowingCntTxtView.setText(("68\n" + getString(R.string.myInfo_following)));
        binding.myInfoTxtView.setText("박성균\ncawa");
        setLayoutHeight();
        setImgs(R.drawable.ic_post_thumb_sel, R.drawable.ic_post_list_desel, R.drawable.ic_story_tag_desel);

        Vector<Integer> postThumbs = new Vector<>();
        for (int i = 1; i <= 10; i++) {
            postThumbs.add(i * i);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        PostThumbAdapter postThumbAdapter = new PostThumbAdapter(getContext());
        binding.postedViews.setLayoutManager(manager);
        binding.postedViews.setAdapter(postThumbAdapter);
        postThumbAdapter.update(postThumbs);
        binding.setListener(this);
        return binding.getRoot();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postThumbListImgView:
                setImgs(R.drawable.ic_post_thumb_sel, R.drawable.ic_post_list_desel, R.drawable.ic_story_tag_desel);
                break;
            case R.id.postListImgView:
                setImgs(R.drawable.ic_post_thumb_desel, R.drawable.ic_post_list_sel, R.drawable.ic_story_tag_desel);
                break;
            case R.id.storyTagListImgView:
                setImgs(R.drawable.ic_post_thumb_desel, R.drawable.ic_post_list_desel, R.drawable.ic_story_tag_sel);
                break;
            case R.id.myInfoUpdateProfileBtn:
                break;
            case R.id.myInfoMenuImgView:
                break;
        }
    }

    private void setLayoutHeight() {
        binding.myInfoTopLayout.getLayoutParams().height = (((getResources().getDisplayMetrics().heightPixels - binding.myInfoToolbar.getHeight()) / 10) * 3);
        binding.myInfoTopInsideLayout.getLayoutParams().height = binding.myInfoTopLayout.getHeight() - binding.postedLayout.getHeight();
    }

    private void setImgs(int... imgs) {
        binding.postThumbListImgView.setImageResource(imgs[0]);
        binding.postListImgView.setImageResource(imgs[1]);
        binding.storyTagListImgView.setImageResource(imgs[2]);
    }

}
