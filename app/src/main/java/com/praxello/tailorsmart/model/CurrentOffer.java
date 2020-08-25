package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentOffer implements Parcelable {
    @SerializedName("offerId")
    @Expose
    public String offerId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("photoLink")
    @Expose
    public String photoLink;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.offerId);
        dest.writeString(this.title);
        dest.writeString(this.details);
        dest.writeString(this.photoLink);
        dest.writeString(this.isActive);
    }

    public CurrentOffer() {
    }

    protected CurrentOffer(Parcel in) {
        this.offerId = in.readString();
        this.title = in.readString();
        this.details = in.readString();
        this.photoLink = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<CurrentOffer> CREATOR = new Parcelable.Creator<CurrentOffer>() {
        @Override
        public CurrentOffer createFromParcel(Parcel source) {
            return new CurrentOffer(source);
        }

        @Override
        public CurrentOffer[] newArray(int size) {
            return new CurrentOffer[size];
        }
    };
}