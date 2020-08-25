package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDatum implements Parcelable {
    @SerializedName("vendorId")
    @Expose
    public String vendorId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(this.vendorId);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
        dest.writeString(this.isActive);
    }

    public VendorDatum() {
    }

    protected VendorDatum(Parcel in) {
        this.vendorId = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<VendorDatum> CREATOR = new Parcelable.Creator<VendorDatum>() {
        @Override
        public VendorDatum createFromParcel(Parcel source) {
            return new VendorDatum(source);
        }

        @Override
        public VendorDatum[] newArray(int size) {
            return new VendorDatum[size];
        }
    };
}