package com.example.jjapstagram_java.post;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ActivityMainPostBinding;
import com.example.jjapstagram_java.util.Logcat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class PostMainActivity extends BaseActivity {

    private ActivityMainPostBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_post);
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        ArrayList<String> folderList = new ArrayList<>();
        String[] folders = file.list();
        for(String folder : folders) {
            Collections.addAll(folderList, folder);
        }
        folderList.add(0, "갤러리");

        binding.gallerySpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_folder, folderList));


        binding.postStopImgView.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}