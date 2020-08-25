package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StyleOrderItem implements Parcelable {
    @SerializedName("orderItemId")
    @Expose
    public String orderItemId;
    @SerializedName("stitchStyleId")
    @Expose
    public String stitchStyleId;
    @SerializedName("stitchSubStyleId")
    @Expose
    public String stitchSubStyleId;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("stitchSubStyleTitle")
    @Expose
    public String stitchSubStyleTitle;
    @SerializedName("stitchStyleTitle")
    @Expose
    public String stitchStyleTitle;
    @SerializedName("stitchStyleDetails")
    @Expose
    public String stitchStyleDetails;
    @SerializedName("stitchStyleType")
    @Expose
    public String stitchStyleType;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getStitchStyleId() {
        return stitchStyleId;
    }

    public void setStitchStyleId(String stitchStyleId) {
        this.stitchStyleId = stitchStyleId;
    }

    public String getStitchSubStyleId() {
        return stitchSubStyleId;
    }

    public void setStitchSubStyleId(String stitchSubStyleId) {
        this.stitchSubStyleId = stitchSubStyleId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStitchSubStyleTitle() {
        return stitchSubStyleTitle;
    }

    public void setStitchSubStyleTitle(String stitchSubStyleTitle) {
        this.stitchSubStyleTitle = stitchSubStyleTitle;
    }

    public String getStitchStyleTitle() {
        return stitchStyleTitle;
    }

    public void setStitchStyleTitle(String stitchStyleTitle) {
        this.stitchStyleTitle = stitchStyleTitle;
    }

    public String getStitchStyleDetails() {
        return stitchStyleDetails;
    }

    public void setStitchStyleDetails(String stitchStyleDetails) {
        this.stitchStyleDetails = stitchStyleDetails;
    }

    public String getStitchStyleType() {
        return stitchStyleType;
    }

    public void setStitchStyleType(String stitchStyleType) {
        this.stitchStyleType = stitchStyleType;
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
        dest.writeString(this.orderItemId);
        dest.writeString(this.stitchStyleId);
        dest.writeString(this.stitchSubStyleId);
        dest.writeString(this.value);
        dest.writeString(this.stitchSubStyleTitle);
        dest.writeString(this.stitchStyleTitle);
        dest.writeString(this.stitchStyleDetails);
        dest.writeString(this.stitchStyleType);
        dest.writeString(this.isActive);
    }

    public StyleOrderItem() {
    }

    protected StyleOrderItem(Parcel in) {
        this.orderItemId = in.readString();
        this.stitchStyleId = in.readString();
        this.stitchSubStyleId = in.readString();
        this.value = in.readString();
        this.stitchSubStyleTitle = in.readString();
        this.stitchStyleTitle = in.readString();
        this.stitchStyleDetails = in.readString();
        this.stitchStyleType = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<StyleOrderItem> CREATOR = new Parcelable.Creator<StyleOrderItem>() {
        @Override
        public StyleOrderItem createFromParcel(Parcel source) {
            return new StyleOrderItem(source);
        }

        @Override
        public StyleOrderItem[] newArray(int size) {
            return new StyleOrderItem[size];
        }
    };
}
