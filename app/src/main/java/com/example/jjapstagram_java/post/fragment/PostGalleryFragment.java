package com.example.jjapstagram_java.post.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.jjapstagram_java.post.ThumbDecoration;
import com.example.jjapstagram_java.post.Thumbnails;

public class PostGalleryFragment extends BaseFragment {

/*    private static PostGalleryFragment instance;

    public static PostGalleryFragment getInstance() {
        return instance;
    }*/

    private FragmentPostGalleryBinding binding;

    private GalleryImageAdapter mGalleryImageAdapter;
    private static int height = 0;
    @Override
    public void setArguments(@Nullable Bundle args) {
        assert args != null;
        height = args.getInt("height");
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

        binding.imgViews.addItemDecoration(new ThumbDecoration());
        //updateFolderPath(getActivity(), "/storage/emulated/0/");
        return binding.getRoot();
    }

    public void updateFolderPath(Activity activity, String path) {
        Log.e("galleryPath", path + "");
        getImageList(activity, path);
    }

    static int count = 0;

    private void getImageList(Activity activity, String path) {
        mGalleryImageAdapter.init();
        Log.e("getImageList", "getImageList");
/*        if (mGalleryImageAdapter == null) {
            mGalleryImageAdapter = new GalleryImageAdapter(getContext());
            binding.imgViews.setAdapter(mGalleryImageAdapter);
        }*/
        String[] mProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
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
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Log.e("total Cnt", cursor.getCount() + "");
            int nCol = cursor.getColumnIndex(mProjection[2]);
            int iId = cursor.getColumnIndex(mProjection[0]);
            int displayname = cursor.getColumnIndex(mProjection[4]);
            while (cursor.moveToNext()) {
                String strImage = cursor.getString(nCol);
                String displayImage = cursor.getString(displayname);
                Log.e("displayName", strImage);
                if (strImage.startsWith(path)) {
                    //long id = cursor.getLong(iId);
                    //Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, options);
                    String title = cursor.getString(3);
                    String data = cursor.getString(2);

                    new Handler().post(() -> mGalleryImageAdapter.update(height, new Thumbnails(title, Uri.parse(data))));
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cursor.close();
    }
}
