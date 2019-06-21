package com.example.jjapstagram_java;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.jjapstagram_java.databinding.ActivityMainBinding;
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
        binding.myInfoImgView.setOnClickListener(this::setFragment);
    }

    private void setHomeFragment(BaseFragment newFragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container, newFragment).commit();
    }

    private void setFragment(View view) {
        switch (view.getId()) {
            case R.id.homeImgView:
                binding.myInfoImgView.setImageResource(R.drawable.ic_my_info_blank);
                setHomeFragment(new HomeMainFragment());
                break;
            case R.id.searchImgView:
                break;
            case R.id.postView:
                break;
            case R.id.likeImgVIew:
                break;
            case R.id.myInfoImgView:
                Log.e("myInfo", "ImgView clicked");
                binding.myInfoImgView.setImageResource(R.drawable.ic_my_info);
                setHomeFragment(new MyInfoMainFragment());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //signOut();
    }

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

