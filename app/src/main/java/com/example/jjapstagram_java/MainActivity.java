package com.example.jjapstagram_java;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.jjapstagram_java.databinding.ActivityMainBinding;
import com.example.jjapstagram_java.home.HomeMainFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    FragmentTransaction mFragmentTransaction;

    FirebaseAuth mFirebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions mGoogleSignInOptions;

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
    private void callGoogleLogin() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);
    }
    private void signOut() {
        callGoogleLogin();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> finish());
    }
}

