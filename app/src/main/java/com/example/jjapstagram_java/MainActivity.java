package com.example.jjapstagram_java;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jjapstagram_java.databinding.ActivityMainBinding;
import com.example.jjapstagram_java.favorite.FavoriteMainFragment;
import com.example.jjapstagram_java.home.HomeMainFragment;
import com.example.jjapstagram_java.login.LoginActivity;
import com.example.jjapstagram_java.myinfo.MyInfoMainFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding binding;
    FragmentTransaction mFragmentTransaction;
    BaseFragment mCurrFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mCurrFragment = new HomeMainFragment();
        setHomeFragment(mCurrFragment);
        binding.homeImgView.setOnClickListener(this::setFragment);
        binding.searchImgView.setOnClickListener(this::setFragment);
        binding.postView.setOnClickListener(this::setFragment);
        binding.likeImgVIew.setOnClickListener(this::setFragment);
        binding.myInfoImgView.setOnClickListener(this::setFragment);
    }

    private void setHomeFragment(BaseFragment newFragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container, newFragment).commit();
    }

    private void setFragment(View view) {
        switch (view.getId()) {
            case R.id.homeImgView:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
                setHomeFragment(new HomeMainFragment());
                break;
            case R.id.searchImgView:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite, R.drawable.ic_my_info_blank);
                break;
            case R.id.postView:
                showFab();
                break;
            case R.id.likeImgVIew:
                setIcons(R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_favorite_fill, R.drawable.ic_my_info_blank);
                setHomeFragment(new FavoriteMainFragment());
                break;
            case R.id.myInfoImgView:
                Log.e("myInfo", "ImgView clicked");
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
        float tY = (float)(binding.fabPostView.getHeight() / 3);
        binding.fabPostView.animate().setDuration(500).translationY(0.0f).translationYBy(tY).scaleX(1.0f).scaleXBy(0.0f).scaleY(1.0f).scaleYBy(0.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.fabPostView.hide();
                binding.postView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showFab() {
        float tY = (float)(binding.fabPostView.getHeight() / 3);
        binding.fabPostView.animate().setDuration(500).translationY(0.0f).translationYBy(-tY).scaleX(0.0f).scaleXBy(1.0f).scaleY(0.0f).scaleYBy(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.fabPostView.show();
                binding.postView.setVisibility(View.INVISIBLE);
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        //signOut();
    }*/

    private void signOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserInfo userInfo = user.getProviderData().get(1);
        if (userInfo.getProviderId().contains("facebook")){
            LoginManager.getInstance().logOut();
        }
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

