package com.example.jjapstagram_java;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Jjapplication extends Application {

    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }
}
