package com.example.jjapstagram_java.post.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentPostGalleryBinding;
import com.example.jjapstagram_java.post.Thumbnails;

public class PostGalleryFragment extends BaseFragment {

    public static PostGalleryFragment fragment;

    public static PostGalleryFragment getInstance() {
        return fragment;
    }

    private FragmentPostGalleryBinding binding;

    private static GalleryImageAdapter mGalleryImageAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_gallery, container, false);
        Log.e("onCreateView", "onCreateView");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        binding.imgViews.setLayoutManager(gridLayoutManager);
        mGalleryImageAdapter = new GalleryImageAdapter(getContext());
        binding.imgViews.setAdapter(mGalleryImageAdapter);
        //updateFolderPath(getActivity(), "/storage/emulated/0/");
        return binding.getRoot();
    }

    public static void updateFolderPath(Activity activity, String path) {
        Log.e("galleryPath", path + "");
        getImageList(activity, path);
    }

    private static void getImageList(Activity activity, String path) {
        Log.e("getImageList", "getImageList");
/*        if (mGalleryImageAdapter == null) {
            mGalleryImageAdapter = new GalleryImageAdapter(getContext());
            binding.imgViews.setAdapter(mGalleryImageAdapter);
        }*/
        String[] mProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        ContentResolver contentResolver = activity.getContentResolver();
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        Uri mImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(mImages,
                mProjection,
                null,
                null,
                orderBy + " DESC");

        //photo data
        int nCol = cursor.getColumnIndex(mProjection[2]);
        int iId = cursor.getColumnIndex(mProjection[0]);
        int count = 0;
        while (cursor.moveToNext() && count < 100) {
            String strImage = cursor.getString(nCol);
            if(strImage.startsWith(path)) {
                if(count % 10 == 0) Log.e("starImage", strImage);
                long id = cursor.getLong(iId);
                Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, null);

                Log.e("cont", count + "");
                String title = cursor.getString(3);
                String data = cursor.getString(2);
                Log.e("title", title + " data " + data);

                mGalleryImageAdapter.update(count, new Thumbnails(title, bitmap, Uri.parse(data)));
            }
            count++;
        }

        cursor.close();
    }
}
