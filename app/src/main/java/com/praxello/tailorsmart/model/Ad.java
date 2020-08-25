package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad implements Parcelable {
    @SerializedName("adId")
    @Expose
    public String adId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("photoUrl")
    @Expose
    public String photoUrl;
    @SerializedName("websitelink")
    @Expose
    public String websitelink;
    @SerializedName("typeOfAd")
    @Expose
    public String typeOfAd; // 0 - main banner, 1 - 1st bottom banner, 2 - 2nd bottom banner

    public String getTypeOfAd() {
        return typeOfAd;
    }

    public void setTypeOfAd(String typeOfAd) {
        this.typeOfAd = typeOfAd;
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

    public Ad() {
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
        dest.writeString(this.typeOfAd);
    }

    protected Ad(Parcel in) {
        this.adId = in.readString();
        this.title = in.readString();
        this.photoUrl = in.readString();
        this.websitelink = in.readString();
        this.typeOfAd = in.readString();
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel source) {
            return new Ad(source);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };
}
