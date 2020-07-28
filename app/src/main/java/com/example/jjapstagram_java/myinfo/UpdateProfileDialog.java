package com.example.jjapstagram_java.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.DialogUpdateprofileBinding;
import com.example.jjapstagram_java.util.Logcat;
import com.example.jjapstagram_java.util.UserInfo;


public class UpdateProfileDialog extends DialogFragment {

    private DialogUpdateprofileBinding binding;
    UserInfo userInfo;

    public UpdateProfileDialog() {
        super();
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        assert args != null;
        userInfo = (UserInfo) args.getSerializable("userInfo");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_updateprofile, container, false);
        setBasicInfo(userInfo);
        return binding.getRoot();
    }

    private void setBasicInfo(UserInfo userInfo) {
        assert getContext() != null;
        Glide.with(getContext()).load(userInfo.getDisPlayPhotoUri()).circleCrop().into(binding.profileImgView);
        binding.profileNameEdtText.setText(userInfo.getUserName());
        binding.profileNicknameEdtText.setText(userInfo.getUserNickName().equals("none") ? null : userInfo.getUserNickName());
        binding.profileStatusMsgEdtText.setText(userInfo.getUserStatusMsg().equals("none") ? null : userInfo.getUserStatusMsg());
        binding.emailAddressEdtText.setText(userInfo.getUserEmail());
        Logcat.e(UpdateProfileDialog.class, userInfo.getUserName(), userInfo.getUserNickName(), userInfo.getUserPhoneNumber(), userInfo.getUserGender());
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeUpdateProfileDialogImgView:
                dismiss();
                break;
            case R.id.updateProfileImgView:
                UserInfo userInfo = new UserInfo();
                userInfo.setUserEmail(this.userInfo.getUserEmail());
                userInfo.setUserName(this.userInfo.getUserName());
                userInfo.setDisPlayPhotoUri(this.userInfo.getDisPlayPhotoUri());
                userInfo.setUserName(binding.profileNameEdtText.getText());
                userInfo.setUserStatusMsg(binding.profileStatusMsgEdtText.getText());
                userInfo.setUserPhoneNumber(binding.phoneEdtText.getText());
                userInfo.setUserGender(binding.genderEdtText.getText());
                break;

            case R.id.genderEdtText:

                break;
        }

    }


}
