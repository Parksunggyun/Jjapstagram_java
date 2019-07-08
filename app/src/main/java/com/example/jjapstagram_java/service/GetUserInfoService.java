package com.example.jjapstagram_java.service;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jjapstagram_java.util.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class GetUserInfoService {

    private AppCompatTextView displayNameTxtView, userNameTxtView;
    private AppCompatImageView profileImgView;

    private FirebaseFirestore mFirebaseFireStore;
    private Activity baseActivity;

    public GetUserInfoService(@NonNull Activity baseActivity, @NonNull AppCompatImageView profileImgView, @NonNull AppCompatTextView... textViews) {
        this.baseActivity = baseActivity;
        this.mFirebaseFireStore = FirebaseFirestore.getInstance();
        this.profileImgView = profileImgView;
        this.displayNameTxtView = textViews[0];
        this.userNameTxtView = textViews[1];
    }


    public void getUserInfo(String userEmail) {
        DocumentReference documentReference = mFirebaseFireStore.collection("users").document(userEmail);
        documentReference.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DocumentSnapshot getUserInfo = task.getResult();
                assert getUserInfo != null;
                UserInfo userInfo = getUserInfo.toObject(UserInfo.class);
                if (userInfo != null) {
                    displayNameTxtView.setText(userInfo.getDisPlayName());
                    if (userInfo.getUserName().equals("none")) {
                        userNameTxtView.setText(userInfo.getDisPlayName());
                    }
                    if (userInfo.getDisPlayPhotoUri() != null) {

                        Glide.with(baseActivity).load(userInfo.getDisPlayPhotoUri()).apply(new RequestOptions().circleCrop()).into(profileImgView);
                    }
                    if (userInfo.getUserStatusMsg().equals("none")) {
                        if (userInfo.getUserName().equals("none")) {
                            userNameTxtView.setText(userInfo.getDisPlayName());
                        } else {
                            userNameTxtView.setText(userInfo.getUserName());
                        }
                    } else {
                        if (userInfo.getUserName().equals("none")) {
                            userNameTxtView.setText(userInfo.getDisPlayName());
                        } else {
                            userNameTxtView.setText(userInfo.getUserName());
                        }
                        userNameTxtView.append("\n");
                        userNameTxtView.append(userInfo.getUserStatusMsg());

                    }
                }
            }

        });

    }

}
