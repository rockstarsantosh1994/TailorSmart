package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerData implements Parcelable {
    @SerializedName("Data")
    @Expose
    public List<Banner> bannerList;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;
    @SerializedName("Message")
    @Expose
    public String message;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public long getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(long responsecode) {
        this.responsecode = responsecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.bannerList);
        dest.writeLong(this.responsecode);
        dest.writeString(this.message);
    }

    public BannerData() {
    }

    protected BannerData(Parcel in) {
        this.bannerList = in.createTypedArrayList(Banner.CREATOR);
        this.responsecode = in.readLong();
        this.message = in.readString();
    }

    public static final Creator<BannerData> CREATOR = new Creator<BannerData>() {
        @Override
        public BannerData createFromParcel(Parcel source) {
            return new BannerData(source);
        }

        @Override
        public BannerData[] newArray(int size) {
            return new BannerData[size];
        }
    };
}
