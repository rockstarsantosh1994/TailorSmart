package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Landmark implements Parcelable {
    @SerializedName("areaName")
    @Expose
    public String areaName;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
        dest.writeString(this.areaName);
        dest.writeString(this.isActive);
    }

    public Landmark() {
    }

    protected Landmark(Parcel in) {
        this.areaName = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<Landmark> CREATOR = new Parcelable.Creator<Landmark>() {
        @Override
        public Landmark createFromParcel(Parcel source) {
            return new Landmark(source);
        }

        @Override
        public Landmark[] newArray(int size) {
            return new Landmark[size];
        }
    };
}
