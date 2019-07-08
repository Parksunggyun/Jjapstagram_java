package com.example.jjapstagram_java.util;

import android.content.Intent;

import com.example.jjapstagram_java.BaseActivity;

public class CommomMethod {

    public static void startActivity(BaseActivity baseActivity, Class destination, boolean finish) {
        Intent intent = new Intent(baseActivity, destination);
        baseActivity.startActivity(intent);
        if(finish) baseActivity.finish();
    }

}
