package com.example.tanamao.entity.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String videoUrl;
    private String videoSource;

    public Video() {
    }

    public Video(String videoUrl, String videoSource) {
        this.videoUrl = videoUrl;
        this.videoSource = videoSource;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    protected Video(Parcel in) {
        videoUrl = in.readString();
        videoSource = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoUrl);
        dest.writeString(videoSource);
    }
}
