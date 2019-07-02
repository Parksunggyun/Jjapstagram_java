package com.example.jjapstagram_java.post;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jjapstagram_java.BR;

public class Post extends BaseObservable {


    private int position;
    private String photo, video;

    Post(int position, String photo, String video) {
        this.position = position;
        this.photo = photo;
        this.video = video;
    }

    @Bindable
    public int getPosition() {
        return position;
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    @Bindable
    public String getVideo() {
        return video;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }

    public void setVideo(String video) {
        this.video = video;
        notifyPropertyChanged(BR.video);
    }
}
