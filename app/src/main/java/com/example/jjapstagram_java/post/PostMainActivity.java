package com.example.jjapstagram_java.post;

import android.content.ContentResolver;
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
import com.example.jjapstagram_java.post.adapter.PostFragmentAdapter;
import com.example.jjapstagram_java.post.fragment.PostGalleryFragment;
import com.example.jjapstagram_java.post.fragment.PostPhotoFragment;
import com.example.jjapstagram_java.util.Logcat;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class PostMainActivity extends BaseActivity {

    private ActivityMainPostBinding binding;
    private PostFragmentAdapter mPostFragmentAdapter;
    private String BASIC_PATH;
    File mFile;

    public int position = 0;
    Post mPost;
    List<String> mFolderList, mFolderPathList;
    SortedSet<String> mFolderSet;
    HashMap<String, String> mFolderMap;

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
            String DETAIL_PATH = mFolderPathList.get(position);
            String LAST_PATH;
            if (!folderName.equals("갤러리")) {
                LAST_PATH = BASIC_PATH + DETAIL_PATH;
            } else {
                LAST_PATH = BASIC_PATH;
            }
            Log.e("LAST_PATH", LAST_PATH);

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

    private void getFolderLists() {
        mFolderMap = new HashMap<>();
        mFolderList = new ArrayList<>();
        mFolderPathList = new ArrayList<>();
        mFolderSet = initFolderLists();
        mFile = Environment.getExternalStorageDirectory().getAbsoluteFile();
        BASIC_PATH = mFile.getPath() + "/";

        for (String folder : mFolderSet) {
            String fold = folder.replace(BASIC_PATH, "");
            String[] folders = fold.split("/");
            String key;
            if (folders.length == 2) {
                key = folders[folders.length - 2] + "/" + folders[folders.length - 1] + "/";
            } else {
                key = folders[folders.length - 1] + "/";
            }
            String value = folders[folders.length - 1];
            mFolderMap.put(key, value);
        }

        List<Map.Entry<String, String>> list = new LinkedList<>(mFolderMap.entrySet());

        Collections.sort(list, (o1, o2) -> {
            int comparision = (o1.getValue().compareTo(o2.getValue()));
            return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
        });
        for (Map.Entry<String, String> key : list) {
            mFolderList.add(mFolderMap.get(key.getKey()));
            mFolderPathList.add(key.getKey());
        }

        mFolderList.add(0, "갤러리");
        mFolderPathList.add(0, "");
        binding.gallerySpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_folder, mFolderList));
    }

    private TreeSet<String> initFolderLists() {
        TreeSet<String> folderSet = new TreeSet<>();
        String[] mProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
        };

        ContentResolver contentResolver = getContentResolver();
        String orderBy = MediaStore.Video.Media.BUCKET_DISPLAY_NAME;
        Uri mImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(mImages,
                mProjection,
                null,
                null,
                orderBy + " DESC");

        //photo data
        assert cursor != null;
        try {
            int nCol = cursor.getColumnIndex(mProjection[1]);
            int displayName = cursor.getColumnIndex(mProjection[2]);
            while (cursor.moveToNext()) {
                String strImage = cursor.getString(nCol);
                String displayImage = cursor.getString(displayName);
                String absolutePath = strImage.replace("/" + displayImage, "");
                folderSet.add(absolutePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cursor.close();
        return folderSet;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}