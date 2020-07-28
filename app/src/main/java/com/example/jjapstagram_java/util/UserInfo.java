package com.example.jjapstagram_java.util;

import java.io.Serializable;

public class UserInfo implements Serializable, Cloneable {

    private String userEmail;
    private String userName;
    private String disPlayPhotoUri;
    private String userNickName;
    private String userPhoneNumber;
    private String userStatusMsg;
    private String userGender;


    public UserInfo(){}

    public UserInfo(String userEmail, String userName, String disPlayPhotoUri, String userNickName,
                    String userPhoneNumber, String userStatusMsg, String userGender) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.disPlayPhotoUri = disPlayPhotoUri;
        this.userNickName = userNickName;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatusMsg = userStatusMsg;
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisPlayPhotoUri() {
        return disPlayPhotoUri;
    }

    public void setDisPlayPhotoUri(String disPlayPhotoUri) {
        this.disPlayPhotoUri = disPlayPhotoUri;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserStatusMsg() {
        return userStatusMsg;
    }

    public void setUserStatusMsg(String userStatusMsg) {
        this.userStatusMsg = userStatusMsg;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
