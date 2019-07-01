package com.example.jjapstagram_java;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jjapstagram_java.databinding.ActivityMainBinding;
import com.example.jjapstagram_java.favorite.FavoriteMainFragment;
import com.example.jjapstagram_java.home.HomeMainFragment;
import com.example.jjapstagram_java.login.LoginActivity;
import com.example.jjapstagram_java.myinfo.MyInfoMainFragment;
import com.example.jjapstagram_java.post.PostMainActivity;
import com.example.jjapstagram_java.search.SearchMainFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int DURATION = 500;


    ActivityMainBinding binding;
    FragmentTransaction mFragmentTransaction;
    BaseFragment mCurrFragment;

    float mOriginY, mTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }

        mCurrFragment = new HomeMainFragment();
        setHomeFragment(mCurrFragment);
        mOriginY = binding.fabPostView.getY();
        mTY = (float) (binding.fabPostView.getHeight() / 3);
        binding.homeImgView.setOnClickListener(this::setFragment);
        binding.searchImgView.setOnClickListener(this::setFragment);
        binding.postView.setOnClickListener(this::setFragment);
        binding.fabPostView.setOnClickListener(this::setFragment);
        binding.likeImgVIew.setOnClickListener(this::setFragment);
        binding.myInfoImgView.setOnClickListener(this::setFragment);
    }

    BaseFragment beforeFragment;

    private void setHomeFragment(BaseFragment newFragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mCurrFragment = newFragment;
        if (mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.replace(R.id.container, mCurrFragment).commit();
            beforeFragment = mCurrFragment;
        } else {
            mFragmentTransaction.detach(beforeFragment);
            mFragmentTransaction.attach(mCurrFragment).commit();
            beforeFragment = mCurrFragment;
        }
    }

    private void setFragment(View view) {
        switch (view.getId()) {
            case R.id.homeImgView:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
                setHomeFragment(new HomeMainFragment());
                break;
            case R.id.searchImgView:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
                setHomeFragment(new SearchMainFragment());
                break;
            case R.id.postView:
            case R.id.fabPostView:
                showFab();
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
                startActivity(new Intent(this, PostMainActivity.class));
                break;
            case R.id.likeImgVIew:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite_fill, R.drawable.ic_my_info_blank);
                setHomeFragment(new FavoriteMainFragment());
                break;
            case R.id.myInfoImgView:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info);
                setHomeFragment(new MyInfoMainFragment());
                break;
        }
    }

    private void setIcons(int... icons) {
        binding.homeImgView.setImageResource(icons[0]);
        binding.searchImgView.setImageResource(icons[1]);
        binding.likeImgVIew.setImageResource(icons[2]);
        binding.myInfoImgView.setImageResource(icons[3]);
        hideFab();
    }

    private void hideFab() {
        binding.fabPostView.animate().setDuration(DURATION).translationY(mOriginY).translationYBy(mTY).scaleX(1.0f).scaleXBy(0.0f).scaleY(1.0f).scaleYBy(0.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.fabPostView.hide();
                binding.postView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showFab() {
        binding.fabPostView.animate().setDuration(DURATION).translationY(mOriginY + mTY).translationYBy(-mOriginY - mTY).scaleX(0.0f).scaleXBy(1.0f).scaleY(0.0f).scaleYBy(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.fabPostView.show();
                binding.postView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mCurrFragment != null && !(mCurrFragment instanceof HomeMainFragment)) {
            setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
            setHomeFragment(new HomeMainFragment());
        } else {
            signOut();
        }
    }

    private void signOut() {
        FirebaseUser user = Jjapplication.getUserInfo();
        UserInfo userInfo = user.getProviderData().get(1);
        if (userInfo.getProviderId().contains("facebook")) {
            LoginManager.getInstance().logOut();
        }
        Jjapplication.mAuth.signOut();
        Jjapplication.mAuth = null;
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }




    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    // 원하는 권한을 배열로 넣어줍니다.
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "granted");
                }
            }
        }
    }
}

