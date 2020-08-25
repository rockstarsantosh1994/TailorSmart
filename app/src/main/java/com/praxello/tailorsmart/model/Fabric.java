package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fabric implements Parcelable {

    @SerializedName("productId")
    @Expose
    public String productId;
    @SerializedName("fabricId")
    @Expose
    public String fabricId;
    @SerializedName("mappedFabricPrice")
    @Expose
    public String mappedFabricPrice;
    @SerializedName("skuNo")
    @Expose
    public String skuNo;
    @SerializedName("fabricTitle")
    @Expose
    public String fabricTitle;
    @SerializedName("fabricDetails")
    @Expose
    public String fabricDetails;
    @SerializedName("fabricPrice")
    @Expose
    public String fabricPrice;
    public String fabricCalculatedPrice;
    @SerializedName("fabricBrand")
    @Expose
    public String fabricBrand;
    @SerializedName("colorName")
    @Expose
    public String colorName;
    @SerializedName("hexColor")
    @Expose
    public String hexColor;
    @SerializedName("fabricType")
    @Expose
    public String fabricType;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    boolean checked = false;

    public String getMappedFabricPrice() {
        return mappedFabricPrice;
    }

    public void setMappedFabricPrice(String mappedFabricPrice) {
        this.mappedFabricPrice = mappedFabricPrice;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getFabricCalculatedPrice() {
        return fabricCalculatedPrice;
    }

    public void setFabricCalculatedPrice(String fabricCalculatedPrice) {
        this.fabricCalculatedPrice = fabricCalculatedPrice;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getFabricBrand() {
        return fabricBrand;
    }

    public void setFabricBrand(String fabricBrand) {
        this.fabricBrand = fabricBrand;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFabricId() {
        return fabricId;
    }

    public void setFabricId(String fabricId) {
        this.fabricId = fabricId;
    }

    public String getFabricTitle() {
        return fabricTitle;
    }

    public void setFabricTitle(String fabricTitle) {
        this.fabricTitle = fabricTitle;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public String getFabricPrice() {
        return fabricPrice;
    }

    public void setFabricPrice(String fabricPrice) {
        this.fabricPrice = fabricPrice;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Fabric() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.fabricId);
        dest.writeString(this.mappedFabricPrice);
        dest.writeString(this.skuNo);
        dest.writeString(this.fabricTitle);
        dest.writeString(this.fabricDetails);
        dest.writeString(this.fabricPrice);
        dest.writeString(this.fabricCalculatedPrice);
        dest.writeString(this.fabricBrand);
        dest.writeString(this.colorName);
        dest.writeString(this.hexColor);
        dest.writeString(this.fabricType);
        dest.writeString(this.isActive);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected Fabric(Parcel in) {
        this.productId = in.readString();
        this.fabricId = in.readString();
        this.mappedFabricPrice = in.readString();
        this.skuNo = in.readString();
        this.fabricTitle = in.readString();
        this.fabricDetails = in.readString();
        this.fabricPrice = in.readString();
        this.fabricCalculatedPrice = in.readString();
        this.fabricBrand = in.readString();
        this.colorName = in.readString();
        this.hexColor = in.readString();
        this.fabricType = in.readString();
        this.isActive = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<Fabric> CREATOR = new Creator<Fabric>() {
        @Override
        public Fabric createFromParcel(Parcel source) {
            return new Fabric(source);
        }

        @Override
        public Fabric[] newArray(int size) {
            return new Fabric[size];
        }
    };
}