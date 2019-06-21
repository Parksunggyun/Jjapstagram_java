package com.example.jjapstagram_java.util;

import android.util.Log;

public class Logcat {

    public static void d(Class clazz, String... infos) {
        Log.d(clazz.getSimpleName(), "===========================================================");

        for (String info : infos) {
            Log.d(clazz.getSimpleName(), info);
        }
        Log.d(clazz.getSimpleName(), "===========================================================");
    }

    public static void e(Class clazz, String... infos) {
        Log.e(clazz.getSimpleName(), "===========================================================");
        for (String info : infos) {
            Log.e(clazz.getSimpleName(), info);
        }
        Log.e(clazz.getSimpleName(), "===========================================================");
    }

}
