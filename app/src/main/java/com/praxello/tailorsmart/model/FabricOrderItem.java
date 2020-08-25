package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FabricOrderItem implements Parcelable {
    @SerializedName("orderItemid")
    @Expose
    public String orderItemid;
    @SerializedName("fabricId")
    @Expose
    public String fabricId;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
    @SerializedName("fabricTitle")
    @Expose
    public String fabricTitle;
    @SerializedName("fabricBrand")
    @Expose
    public String fabricBrand;
    @SerializedName("fabricDetails")
    @Expose
    public String fabricDetails;
    @SerializedName("skuNo")
    @Expose
    public String skuNo;
    @SerializedName("fabricPrice")
    @Expose
    public String fabricPrice;
    @SerializedName("releaseDate")
    @Expose
    public String releaseDate;
    @SerializedName("isPriceVariable")
    @Expose
    public String isPriceVariable;
    @SerializedName("hexColor")
    @Expose
    public String hexColor;
    @SerializedName("colorName")
    @Expose
    public String colorName;
    @SerializedName("fabricType")
    @Expose
    public String fabricType;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getOrderItemid() {
        return orderItemid;
    }

    public void setOrderItemid(String orderItemid) {
        this.orderItemid = orderItemid;
    }

    public String getFabricId() {
        return fabricId;
    }

    public void setFabricId(String fabricId) {
        this.fabricId = fabricId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFabricTitle() {
        return fabricTitle;
    }

    public void setFabricTitle(String fabricTitle) {
        this.fabricTitle = fabricTitle;
    }

    public String getFabricBrand() {
        return fabricBrand;
    }

    public void setFabricBrand(String fabricBrand) {
        this.fabricBrand = fabricBrand;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getFabricPrice() {
        return fabricPrice;
    }

    public void setFabricPrice(String fabricPrice) {
        this.fabricPrice = fabricPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getIsPriceVariable() {
        return isPriceVariable;
    }

    public void setIsPriceVariable(String isPriceVariable) {
        this.isPriceVariable = isPriceVariable;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
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
        dest.writeString(this.orderItemid);
        dest.writeString(this.fabricId);
        dest.writeString(this.categoryId);
        dest.writeString(this.fabricTitle);
        dest.writeString(this.fabricBrand);
        dest.writeString(this.fabricDetails);
        dest.writeString(this.skuNo);
        dest.writeString(this.fabricPrice);
        dest.writeString(this.releaseDate);
        dest.writeString(this.isPriceVariable);
        dest.writeString(this.hexColor);
        dest.writeString(this.colorName);
        dest.writeString(this.fabricType);
        dest.writeString(this.isActive);
    }

    public FabricOrderItem() {
    }

    protected FabricOrderItem(Parcel in) {
        this.orderItemid = in.readString();
        this.fabricId = in.readString();
        this.categoryId = in.readString();
        this.fabricTitle = in.readString();
        this.fabricBrand = in.readString();
        this.fabricDetails = in.readString();
        this.skuNo = in.readString();
        this.fabricPrice = in.readString();
        this.releaseDate = in.readString();
        this.isPriceVariable = in.readString();
        this.hexColor = in.readString();
        this.colorName = in.readString();
        this.fabricType = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<FabricOrderItem> CREATOR = new Parcelable.Creator<FabricOrderItem>() {
        @Override
        public FabricOrderItem createFromParcel(Parcel source) {
            return new FabricOrderItem(source);
        }

        @Override
        public FabricOrderItem[] newArray(int size) {
            return new FabricOrderItem[size];
        }
    };
}
