package com.example.jjapstagram_java.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.MainActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ActivityLoginBinding;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final int GOOGLE_LOGIN_IN = 999;
    private final String FACEBOOK_LOGIN_KEY = "email", FACEBOOK_LOGIN_VALUE = "public_profile";

    private ActivityLoginBinding binding;

    GoogleSignInOptions mGoogleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mFirebaseAuth;

    String mUserName, mUserEmail;


    CallbackManager mCallbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        callGoogleLogin();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mFirebaseAuth = FirebaseAuth.getInstance();
        TextView textView = (TextView) binding.googleSignInBtn.getChildAt(0);
        textView.setText(getString(R.string.sign_in_google));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        binding.facebookSIgnInBtn.setLoginText(getString(R.string.sign_in_facebook));
        binding.googleSignInBtn.setOnClickListener(this::signIn);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        checkUser(currentUser);
    }

    private void callGoogleLogin() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_LOGIN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was Successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Log.w(TAG, "Google sign in failed", e);
                //updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle : " + account.getId());

        showProgresDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        assert user != null;
                        mUserName = user.getDisplayName();
                        mUserEmail = user.getEmail();
                        //String userPhoneNumber = user.getPhoneNumber();
                        checkUser(user);

                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(binding.loginLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        checkUser(null);
                    }

                    hideProgressDialog();
                });
    }


    private void signIn(View view) {
        switch (view.getId()) {
            case R.id.googleSignInBtn:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_LOGIN_IN);
                break;
            case R.id.facebookSIgnInBtn:
                mCallbackManager = CallbackManager.Factory.create();
                binding.facebookSIgnInBtn.setPermissions(FACEBOOK_LOGIN_KEY);
                binding.facebookSIgnInBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        //handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                break;
        }
    }

    private void checkUser(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userEmail", mUserEmail);
            intent.putExtra("mUserName", mUserName);
            startActivity(intent);
            finish();
        } else {
            checkUser();

        }
    }

    private void checkUser() {

    }

}
