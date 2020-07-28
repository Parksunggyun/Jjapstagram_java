package com.example.jjapstagram_java.post.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jjapstagram_java.BaseFragment;
import com.example.jjapstagram_java.MainActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.FragmentPostGalleryBinding;
import com.example.jjapstagram_java.post.ThumbDecoration;
import com.example.jjapstagram_java.post.Thumbnails;
import com.example.jjapstagram_java.post.adapter.GalleryImageAdapter;

import java.io.File;

public class PostGalleryFragment extends BaseFragment {

    /*    private static PostGalleryFragment instance;

        public static PostGalleryFragment getInstance() {
            return instance;
        }*/
    private static final String TAG = PostGalleryFragment.class.getSimpleName();

    private FragmentPostGalleryBinding binding;

    private GalleryImageAdapter mGalleryImageAdapter;
    private static int height = 0;

    private boolean singleImage = true;
    private int currPos, prevPos = -1, cnt = 1;

    @Override
    public void setArguments(@Nullable Bundle args) {
        assert args != null;
        height = args.getInt("height");
    }

    private View.OnTouchListener touchListener = (v, event) -> {
        switch (v.getId()) {
            case R.id.selectImagesImgView: {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    if (singleImage) {
                        binding.selectImagesImgView.setBackgroundResource(R.drawable.border_more_image_sel);
                        mGalleryImageAdapter.setMultiSelectMode(true, currPos, cnt);
                        singleImage = false;
                    } else {
                        binding.selectImagesImgView.setBackgroundResource(R.drawable.border_more_image);
                        singleImage = true;
                        mGalleryImageAdapter.setSingSelectMode(false);
                        prevPos = 0;
                        currPos = 0;
                        cnt = 1;
                    }
                    return true;
                }
            }
        }
        return true;
    };

    private void onClick(View view) {
        Log.e(TAG, "ㅐㅜ치ㅑ차");
        switch (view.getId()) {
            case R.id.selectImagesImgView:
                if (singleImage) {
                    binding.selectImagesImgView.setBackgroundResource(R.drawable.border_more_image_sel);
                    mGalleryImageAdapter.setMultiSelectMode(true, currPos, cnt);
                    singleImage = false;
                } else {
                    binding.selectImagesImgView.setBackgroundResource(R.drawable.border_more_image);
                    singleImage = true;
                    mGalleryImageAdapter.setSingSelectMode(false);
                    prevPos = 0;
                    currPos = 0;
                    cnt = 1;
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_gallery, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        binding.imgViews.setLayoutManager(gridLayoutManager);
        mGalleryImageAdapter = new GalleryImageAdapter(getContext());
        binding.imgViews.setAdapter(mGalleryImageAdapter);
        binding.imgViews.addOnItemTouchListener(onItemTouchListener);
        binding.imgViews.addOnScrollListener(onScrollChangeListener);
        binding.imgViews.addItemDecoration(new ThumbDecoration());
        binding.selectImagesImgView.setOnClickListener(this::onClick);
        binding.selectImagesImgView.setOnTouchListener(touchListener);
        return binding.getRoot();
    }

    public void updateFolderPath(Activity activity, String path) {
        Log.e("galleryPath", path + "");
        getImageList(activity, path);
    }

    Cursor cursor;

    private void getImageList(Activity activity, String path) {
        Log.e("firstImagePath", path + "");
        mGalleryImageAdapter.init();
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
        cursor = contentResolver.query(mImages,
                mProjection,
                null,
                null,
                orderBy + " DESC");

        //photo data
        try {
            assert cursor != null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            int nCol = cursor.getColumnIndex(mProjection[2]);
            int iId = cursor.getColumnIndex(mProjection[0]);
            int count = 0;
            while (cursor.moveToNext()) {
                String strImage = cursor.getString(nCol);
                if (strImage.startsWith(path)) {
                    String title = cursor.getString(3);
                    String data = cursor.getString(2);
                    Thumbnails thumbnail = new Thumbnails(title, Uri.parse(data));
                    if (count == 0) {
                        thumbnail.setSelect(true);
                        String firstImagePath = thumbnail.getUri().getPath();
                        Log.e("firstImagePath isFirst", firstImagePath + "");
                        Glide.with(this).load(new File(firstImagePath)).into(binding.selectedImgView);
                    }
                    //long id = cursor.getLong(iId);
                    //Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, options);
                    count++;
                    new Handler().post(() -> mGalleryImageAdapter.update(height, thumbnail));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
    }

    private boolean imageTouch;

    private RecyclerView.OnScrollListener onScrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Scroll 시 ACTION_UP 이벤트 막기 위함
            imageTouch = false;
        }
    };

    private RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView parent, @NonNull MotionEvent evt) {
            if (MotionEvent.ACTION_UP == evt.getAction() && imageTouch) {

                assert getContext() != null;
                View child = parent.findChildViewUnder(evt.getX(), evt.getY());
                assert child != null;
                currPos = parent.getChildAdapterPosition(child);
                if (currPos != -1) {
                    Thumbnails thumbInfo = mGalleryImageAdapter.getThumbInfo(currPos);
                    mGalleryImageAdapter.setSelected(currPos, prevPos);
                    Glide.with(getContext()).load(new File(thumbInfo.getUri().getPath())).thumbnail(0.5f).into(binding.selectedImgView);

                    if (!singleImage) {
                        if (thumbInfo.getSelectPosition() == -1) {
                            cnt++;
                            thumbInfo.setSelectPosition(cnt);
                            prevPos = currPos;
                        } else {
                            mGalleryImageAdapter.modifySelectedPosition(currPos);
                            mGalleryImageAdapter.getThumbInfo(prevPos).setSelect(true);
                            cnt--;
                            thumbInfo.setSelectPosition(-1);
                            thumbInfo.setSelect(false);
                        }

                    } else {
                        if (prevPos == currPos) {
                            mGalleryImageAdapter.setSelected(currPos, prevPos);
                        }
                        prevPos = currPos;
                    }
                }

            } else if (MotionEvent.ACTION_DOWN == evt.getAction()) {
                imageTouch = true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}
