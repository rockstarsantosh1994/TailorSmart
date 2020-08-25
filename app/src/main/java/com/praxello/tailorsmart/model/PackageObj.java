package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageObj implements Parcelable {
    @SerializedName("packageId")
    @Expose
    public String packageId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("subTitle")
    @Expose
    public String subTitle;
    @SerializedName("exterior")
    @Expose
    public String exterior;
    @SerializedName("interior")
    @Expose
    public String interior;
    @SerializedName("underBody")
    @Expose
    public String underBody;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("cost")
    @Expose
    public String cost;
    @SerializedName("gst")
    @Expose
    public String gst;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getUnderBody() {
        return underBody;
    }

    public void setUnderBody(String underBody) {
        this.underBody = underBody;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
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
        dest.writeString(this.packageId);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.exterior);
        dest.writeString(this.interior);
        dest.writeString(this.underBody);
        dest.writeString(this.type);
        dest.writeString(this.cost);
        dest.writeString(this.gst);
        dest.writeString(this.isActive);
    }

    public PackageObj() {
    }

    protected PackageObj(Parcel in) {
        this.packageId = in.readString();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.exterior = in.readString();
        this.interior = in.readString();
        this.underBody = in.readString();
        this.type = in.readString();
        this.cost = in.readString();
        this.gst = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<PackageObj> CREATOR = new Parcelable.Creator<PackageObj>() {
        @Override
        public PackageObj createFromParcel(Parcel source) {
            return new PackageObj(source);
        }

        @Override
        public PackageObj[] newArray(int size) {
            return new PackageObj[size];
        }
    };
}
