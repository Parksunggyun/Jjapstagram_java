package com.example.jjapstagram_java.service;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jjapstagram_java.util.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class GetUserInfoService {

    private AppCompatTextView userNickNameTxtView, userStatusTxtView;
    private AppCompatImageView profileImgView;

    private FirebaseFirestore mFirebaseFireStore;
    private Activity baseActivity;

    public GetUserInfoService(@NonNull Activity baseActivity, @NonNull AppCompatImageView profileImgView, @NonNull AppCompatTextView... textViews) {
        this.baseActivity = baseActivity;
        this.mFirebaseFireStore = FirebaseFirestore.getInstance();
        this.profileImgView = profileImgView;
        this.userNickNameTxtView = textViews[0];
        this.userStatusTxtView = textViews[1];
    }

    private UserInfo mUserInfo;


    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo userInfo) throws CloneNotSupportedException {
        this.mUserInfo = (UserInfo) userInfo.clone();
    }

    public void getUserInfo(String userEmail) {
        DocumentReference documentReference = mFirebaseFireStore.collection("users").document(userEmail);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
            if (userInfo != null) {
                try {
                    setmUserInfo(userInfo);
                    if (userInfo.getUserNickName().equals("none")) {
                        userNickNameTxtView.setText(userInfo.getUserName());
                    }
                    if (userInfo.getDisPlayPhotoUri() != null) {

                        Glide.with(baseActivity).load(userInfo.getDisPlayPhotoUri()).apply(new RequestOptions().circleCrop()).into(profileImgView);
                    }
                    if (userInfo.getUserStatusMsg().equals("none")) {
                        if (userInfo.getUserName().equals("none")) {
                            userStatusTxtView.setText(userInfo.getUserName());
                        } else {
                            userStatusTxtView.setText(userInfo.getUserName());
                        }
                    } else {
                        if (userInfo.getUserName().equals("none")) {
                            userStatusTxtView.setText(userInfo.getUserName());
                        } else {
                            userStatusTxtView.setText(userInfo.getUserName());
                        }
                        userStatusTxtView.append("\n");
                        userStatusTxtView.append(userInfo.getUserStatusMsg());

                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
