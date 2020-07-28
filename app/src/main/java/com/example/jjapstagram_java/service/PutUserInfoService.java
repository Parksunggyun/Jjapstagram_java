package com.example.jjapstagram_java.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.MainActivity;
import com.example.jjapstagram_java.login.LoginActivity;
import com.example.jjapstagram_java.util.CommomMethod;
import com.example.jjapstagram_java.util.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PutUserInfoService {

    private FirebaseFirestore mFirebaseFireStore;

    private BaseActivity activity;

    private ProgressDialog progressDialog;

    public PutUserInfoService(BaseActivity activity) {
        this.activity = activity;
        mFirebaseFireStore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(activity);
    }

    public void UpdateUserInfo(UserInfo userInfo) {
        showProgressDialog();
        DocumentReference documentReference = mFirebaseFireStore.collection("users").document(userInfo.getUserEmail());
        Map<String, String> mUserInfo = new HashMap<>();
        mUserInfo.put("userEmail", userInfo.getUserEmail());
        mUserInfo.put("userName", userInfo.getUserName());
        mUserInfo.put("disPlayPhotoUri", userInfo.getDisPlayPhotoUri());
        mUserInfo.put("userNickName", userInfo.getUserNickName());
        mUserInfo.put("userStatusMsg", userInfo.getUserStatusMsg());
        mUserInfo.put("userPhoneNumber", userInfo.getUserPhoneNumber());
        mUserInfo.put("userGender", userInfo.getUserGender());
        documentReference.set(mUserInfo).addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                Toast.makeText(activity, "회원 정보가 정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                CommomMethod.startActivity(activity, MainActivity.class, true);
            } else {
                Toast.makeText(activity, "일시적인 네트워크 에러가 발생했습니다. 잠시 후 다시시도해주세요.", Toast.LENGTH_SHORT).show();
                ((LoginActivity) activity).signOut();
            }
            hideProgressDialog();
        });

    }

    public void PutUserInfo(String userEmail, String userName, String userPhotoUri) {
        showProgressDialog();
        DocumentReference documentReference = mFirebaseFireStore.collection("users").document(userEmail);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot userInfoSnapshot = task.getResult();
                if (userInfoSnapshot.getData() == null) {
                    Map<String, String> mUserInfo = new HashMap<>();
                    mUserInfo.put("userEmail", userEmail);
                    mUserInfo.put("userName", userName);
                    mUserInfo.put("disPlayPhotoUri", userPhotoUri);
                    mUserInfo.put("userNickName", "none");
                    mUserInfo.put("userStatusMsg", "none");
                    mUserInfo.put("userPhoneNumber", "none");
                    mUserInfo.put("userGender", "none");
                    documentReference.set(mUserInfo).addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            Toast.makeText(activity, "회원이 되신 걸 축하드립니다.", Toast.LENGTH_SHORT).show();
                            CommomMethod.startActivity(activity, MainActivity.class, true);
                        } else {
                            Toast.makeText(activity, "일시적인 네트워크 에러가 발생했습니다. 잠시 후 다시시도해주세요.", Toast.LENGTH_SHORT).show();
                            ((LoginActivity) activity).signOut();
                        }
                    });
                } else {
                    CommomMethod.startActivity(activity, MainActivity.class, true);
                }

                hideProgressDialog();
            }
        });
    }

    private void showProgressDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("잠시만 기다려주세요...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
        progressDialog = null;
    }

}
