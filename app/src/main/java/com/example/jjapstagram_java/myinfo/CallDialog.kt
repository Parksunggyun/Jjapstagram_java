package com.example.jjapstagram_java.myinfo

import android.content.Context
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.jjapstagram_java.R
import com.example.jjapstagram_java.util.Logcat

class CallDialog (val context: Context) {

    fun createDialog() {
        MaterialDialog(context).show {
            listItemsSingleChoice(R.array.gender)
            positiveButton {
                Log.e("asdas", "asdsad")
            }
        }
    }

}