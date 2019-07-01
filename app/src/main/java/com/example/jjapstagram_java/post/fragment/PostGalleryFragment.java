package com.example.jjapstagram_java.post.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.util.Logcat;

public class PostGalleryFragment extends BaseFragment {

    private String[] mProjection = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
    };

    private Uri mImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getActivity() != null;
        Cursor mCursor = getActivity().getContentResolver().query(mImages,
                mProjection,
                null,
                null,
                null);
        Logcat.e(getClass(), String.valueOf(mCursor.getCount()));
        mCursor.close();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
