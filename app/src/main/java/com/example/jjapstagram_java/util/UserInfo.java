package com.example.jjapstagram_java.util;

import androidx.annotation.NonNull;

public class UserInfo {

    private String disPlayName;
    private String disPlayPhotoUri;
    private String userName;
    private String userPhoneNumber;
    private String userStatusMsg;

    public UserInfo(){}

    public UserInfo(@NonNull String disPlayName, @NonNull String disPlayPhotoUri, @NonNull String userName, @NonNull String userPhoneNumber, @NonNull String userStatusMsg) {
        this.disPlayName = disPlayName;
        this.disPlayPhotoUri = disPlayPhotoUri;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatusMsg = userStatusMsg;
    }

    public String getDisPlayName() {
        return disPlayName;
    }

    public String getDisPlayPhotoUri() {
        return disPlayPhotoUri;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserStatusMsg() {
        return userStatusMsg;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setDisPlayName(String disPlayName) {
        this.disPlayName = disPlayName;
    }

    public void setDisPlayPhotoUri(String disPlayPhotoUri) {
        this.disPlayPhotoUri = disPlayPhotoUri;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserStatusMsg(String userStatusMsg) {
        this.userStatusMsg = userStatusMsg;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
