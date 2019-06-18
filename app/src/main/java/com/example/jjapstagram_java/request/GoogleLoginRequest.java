package com.example.jjapstagram_java.request;


import android.util.Log;
import android.widget.Toast;

import com.example.jjapstagram_java.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLoginRequest {

    private static final String TAG = GoogleLoginRequest.class.getSimpleName();

    private LoginActivity activity;
    private FirebaseAuth firebaseAuth;

    public GoogleLoginRequest(LoginActivity activity) {
        this.activity = activity;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle : " + account.getId());

        activity.showProgresDialog();

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        assert task.getResult() != null;
                        String provider = task.getResult().getCredential().getProvider();
                        Log.d(TAG, "google - signInWithCredential:success");
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        assert firebaseUser != null;
                        Log.d(TAG, provider);
                        activity.checkUser(firebaseUser, provider);
                    } else {
                        Log.w(TAG, "google - signInWithCredential:failure", task.getException());
                        Toast.makeText(activity, "facebook - Authentication Failed.", Toast.LENGTH_SHORT).show();
                        activity.checkUser(null, null);
                    }

                    activity.hideProgressDialog();
                });
    }


}
