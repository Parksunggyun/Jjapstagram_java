package com.example.jjapstagram_java.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLoginRequest implements LoginRequest {

    private static final String TAG = GoogleLoginRequest.class.getSimpleName();

    GoogleSignInOptions mGoogleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mFirebaseAuth;

    private Context context;
    private BaseActivity activity;
    private ConstraintLayout loginLayout;

    public GoogleLoginRequest(Context context, BaseActivity activity, ConstraintLayout loginLayout) {
        this.context = context;
        this.activity = activity;
        this.loginLayout = loginLayout;
    }

    @Override
    public void request(int type) {
        callGoogleLogin();

        mGoogleSignInClient = GoogleSignIn.getClient(context, mGoogleSignInOptions);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    private void callGoogleLogin() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle : " + account.getId());

        activity.showProgresDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        assert user != null;
                        String userName = user.getDisplayName();
                        String userEmail = user.getEmail();
                        String userPhoneNumber = user.getPhoneNumber();
                        Log.d("mUserName", userName);
                        Log.d("userEmail", userEmail);
                        Log.d("userPhoneNumber", "[" + userPhoneNumber + "]");
                        //updateUI(user);

                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(loginLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        //updateUI(null);
                    }

                    activity.hideProgressDialog();
                });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, LoginActivity.GOOGLE_LOGIN_IN);
    }
}
