package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner implements Parcelable {
    @SerializedName("adId")
    @Expose
    public String adId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("photoUrl")
    @Expose
    public String photoUrl;
    @SerializedName("url")
    @Expose
    public String websitelink;
    @SerializedName("mediaType")
    @Expose
    public String mediaType;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.websitelink = websitelink;
    }

    public Banner() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adId);
        dest.writeString(this.title);
        dest.writeString(this.photoUrl);
        dest.writeString(this.websitelink);
        dest.writeString(this.mediaType);
    }

    protected Banner(Parcel in) {
        this.adId = in.readString();
        this.title = in.readString();
        this.photoUrl = in.readString();
        this.websitelink = in.readString();
        this.mediaType = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}