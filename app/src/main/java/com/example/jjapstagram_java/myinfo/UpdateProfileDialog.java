package com.example.jjapstagram_java.myinfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.DialogUpdateprofileBinding;


public class UpdateProfileDialog extends Dialog {

    private DialogUpdateprofileBinding binding;

    public UpdateProfileDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_updateprofile, null, false);

    }


}
