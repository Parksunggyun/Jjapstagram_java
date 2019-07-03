package com.example.jjapstagram_java.post;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ActivityMainPostBinding;
import com.example.jjapstagram_java.post.fragment.PostFragmentAdapter;
import com.example.jjapstagram_java.post.fragment.PostGalleryFragment;
import com.example.jjapstagram_java.post.fragment.PostPhotoFragment;
import com.example.jjapstagram_java.util.Logcat;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PostMainActivity extends BaseActivity {

    private ActivityMainPostBinding binding;
    private PostFragmentAdapter mPostFragmentAdapter;
    private String BASIC_PATH, DETAIL_PATH, LAST_PATH;
    File mFile;

    public int position = 0;
    Post mPost;
    List<String> mFolderList;
    Set<String> mFolderSet;

    Fragment mCurrentFragment;
    int quarterWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_post);
        quarterWidth = getResources().getDisplayMetrics().widthPixels / 4;
        setFragments();
        binding.postStopImgView.setOnClickListener(view -> onBackPressed());
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.e("onItemSelected", "called");

            String folderName = (String) binding.gallerySpinner.getSelectedItem();
            DETAIL_PATH = folderName;
            if (!folderName.equals("갤러리")) {
                LAST_PATH = BASIC_PATH + DETAIL_PATH + "/";
            } else {
                LAST_PATH = BASIC_PATH;
            }

            mCurrentFragment = mPostFragmentAdapter.getItem(PostMainActivity.this.position);
            //PostGalleryFragment.updateFolderPath(PostMainActivity.this, LAST_PATH);
            if (mCurrentFragment instanceof PostGalleryFragment) {
                ((PostGalleryFragment) mCurrentFragment).updateFolderPath(PostMainActivity.this, LAST_PATH);
            } else if (mCurrentFragment instanceof PostPhotoFragment) {
                Logcat.e(PostMainActivity.class, "photo");
            } else {
                Logcat.e(PostMainActivity.class, folderName, "video");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setFragments() {
        getFolderLists();
        mPostFragmentAdapter = new PostFragmentAdapter(getSupportFragmentManager(), quarterWidth);
        binding.postViewPager.setAdapter(mPostFragmentAdapter);
        binding.postViewPager.setOffscreenPageLimit(2);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_gallery)), 0);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_photo)), 1);
        binding.postTabLayout.addTab(binding.postTabLayout.newTab().setText(getString(R.string.txt_video)), 2);
        mPost = new Post(position, getString(R.string.txt_photo), getString(R.string.txt_video));
        binding.setPost(mPost);
        binding.postTabLayout.addOnTabSelectedListener(new PostTabListener(binding.postViewPager, mPost, position));
        binding.postViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.postTabLayout));

        binding.gallerySpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    private ArrayList<String> getBucketNames() {
        ArrayList<String> folderLists = new ArrayList<>();
        mFolderSet = new HashSet<>();
/*        String[] projection = new String[] {
                "Distinct " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };*/

        String[] projection = new String[] {"DISTINCT " + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            String bucket;
            int bucketColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME);

            do {
                bucket = cursor.getString(bucketColumn);
                Log.e("folderName", bucket);
                mFolderSet.add(bucket);
            } while (cursor.moveToNext());
        }
        for (String folderName : mFolderSet) {
            folderLists.add(folderName);
            //Logcat.e(PostMainActivity.class, folderName);
        }
        return folderLists;
    }

    private void getFolderLists() {
        mFolderList = new ArrayList<>();
        //mFolderList = getBucketNames();
/*        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String[] folders = file.list();
        for (String folder : folders) {
            Collections.addAll(mFolderList, folder);
        }*/
        mFolderList.add(0, "갤러리");
        binding.gallerySpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_folder, mFolderList));
        mFile = Environment.getExternalStorageDirectory().getAbsoluteFile();
        BASIC_PATH = mFile.getPath() + "/";
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}