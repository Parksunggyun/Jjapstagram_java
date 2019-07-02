package com.example.jjapstagram_java.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ActivityMainPostBinding;
import com.example.jjapstagram_java.post.fragment.PostFragmentAdapter;
import com.example.jjapstagram_java.post.fragment.PostGalleryFragment;
import com.example.jjapstagram_java.post.fragment.PostPhotoFragment;
import com.example.jjapstagram_java.util.Logcat;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class PostMainActivity extends BaseActivity {

    private ActivityMainPostBinding binding;
    private PostFragmentAdapter mPostFragmentAdapter;
    private String BASIC_PATH, DETAIL_PATH, LAST_PATH;
    File mFile;

    public int position;
    Post mPost;
    ArrayList<String> mFolderList;

    Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_post);

        setFragments();
        binding.postStopImgView.setOnClickListener(view -> onBackPressed());
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String folderName = (String) binding.gallerySpinner.getSelectedItem();
            DETAIL_PATH = folderName;
            if (!folderName.equals("갤러리")) {
                LAST_PATH = BASIC_PATH + DETAIL_PATH + "/";
            } else {
                LAST_PATH = BASIC_PATH;
            }
            //mCurrentFragment = PostGalleryFragment.getInstance();
            PostGalleryFragment.getInstance().updateFolderPath(PostMainActivity.this, LAST_PATH);
/*            if (mCurrentFragment instanceof PostGalleryFragment) {
                ((PostGalleryFragment) mCurrentFragment).updateFolderPath(PostMainActivity.this, LAST_PATH);
            } else if (mCurrentFragment instanceof PostPhotoFragment) {
                Logcat.e(PostMainActivity.class, "photo");
            } else {
                Logcat.e(PostMainActivity.class, folderName, "video");
            }*/

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setFragments() {
        mPostFragmentAdapter = new PostFragmentAdapter(getSupportFragmentManager());
        binding.postViewPager.setAdapter(mPostFragmentAdapter);
        binding.postViewPager.setOffscreenPageLimit(2);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_gallery)), 0);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_photo)), 1);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_video)), 2);

        binding.postTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        binding.postViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.postTabLayout));



        mPost = new Post(position, getString(R.string.txt_photo), getString(R.string.txt_video));
        binding.setPost(mPost);
        getFolderLists();
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            position = tab.getPosition();
            mPost.setPosition(position);
            binding.postViewPager.setCurrentItem(position);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void getFolderLists() {
        mFolderList = new ArrayList<>();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String[] folders = file.list();
        for (String folder : folders) {
            Collections.addAll(mFolderList, folder);
        }
        mFolderList.add(0, "갤러리");
        binding.gallerySpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_folder, mFolderList));


        binding.gallerySpinner.setOnItemSelectedListener(onItemSelectedListener);
        mFile = Environment.getExternalStorageDirectory().getAbsoluteFile();
        BASIC_PATH = mFile.getPath() + "/";
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}