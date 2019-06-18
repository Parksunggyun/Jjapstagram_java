package com.example.jjapstagram_java;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jjapstagram_java.databinding.ActivityMainBinding;
import com.example.jjapstagram_java.home.HomeMainFragment;
import com.example.jjapstagram_java.login.LoginActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setHomeFragment();
    }

    private void setHomeFragment() {
        if (mFragmentTransaction != null) {
            mFragmentTransaction = null;
        }
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.container, new HomeMainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        signOut();
    }

    private void signOut() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.login_name), MODE_PRIVATE);
        String provider = sharedPreferences.getString(getString(R.string.login_provider), null);
        assert provider != null;
        if(provider.contains("facebook")) {
            LoginManager.getInstance().logOut();
        }
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
/*
        mCallbackManager = CallbackManager.Factory.create();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });*/
    }
}

