package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measurement implements Parcelable {
    @SerializedName("orderItemid")
    @Expose
    public String orderItemid;
    @SerializedName("measurementId")
    @Expose
    public String measurementId;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("itemTitle")
    @Expose
    public String itemTitle;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("customerId")
    @Expose
    public String customerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderItemid() {
        return orderItemid;
    }

    public void setOrderItemid(String orderItemid) {
        this.orderItemid = orderItemid;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Measurement() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderItemid);
        dest.writeString(this.measurementId);
        dest.writeString(this.value);
        dest.writeString(this.itemTitle);
        dest.writeString(this.isActive);
        dest.writeString(this.id);
        dest.writeString(this.customerId);
    }

    protected Measurement(Parcel in) {
        this.orderItemid = in.readString();
        this.measurementId = in.readString();
        this.value = in.readString();
        this.itemTitle = in.readString();
        this.isActive = in.readString();
        this.id = in.readString();
        this.customerId = in.readString();
    }

    public static final Creator<Measurement> CREATOR = new Creator<Measurement>() {
        @Override
        public Measurement createFromParcel(Parcel source) {
            return new Measurement(source);
        }

        @Override
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };
}