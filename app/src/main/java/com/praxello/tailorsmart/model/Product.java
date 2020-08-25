package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {
    @SerializedName("productId")
    @Expose
    public String productId;
    @SerializedName("parentId")
    @Expose
    public String parentId;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
    @SerializedName("styleId")
    @Expose
    public String styleId;
    @SerializedName("subStyleId")
    @Expose
    public String subStyleId;
    @SerializedName("productTitle")
    @Expose
    public String productTitle;
    @SerializedName("productSubTitle")
    @Expose
    public String productSubTitle;
    @SerializedName("productDetails")
    @Expose
    public String productDetails;
    @SerializedName("price")
    @Expose
    public String price;
    public String calculatedPrice;
    @SerializedName("releaseDate")
    @Expose
    public String releaseDate;
    @SerializedName("expiryDate")
    @Expose
    public String expiryDate;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("categoryTitle")
    @Expose
    public String categoryTitle;
    @SerializedName("productStyleId")
    @Expose
    public String productStyleId;
    @SerializedName("styleTitle")
    @Expose
    public String styleTitle;
    @SerializedName("subStyleTitle")
    @Expose
    public String subStyleTitle;
    @SerializedName("isPriceVariable")
    @Expose
    public String isPriceVariable;
    @SerializedName("sequenceNo")
    @Expose
    public String sequenceNo;
    @SerializedName("isGroup")
    @Expose
    public String isGroup;
    public boolean checked;
    public ArrayList<Fabric> fabricList;

    public String getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(String calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsPriceVariable() {
        return isPriceVariable;
    }

    public void setIsPriceVariable(String isPriceVariable) {
        this.isPriceVariable = isPriceVariable;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public ArrayList<Fabric> getFabricList() {
        return fabricList;
    }

    public void setFabricList(ArrayList<Fabric> fabricList) {
        this.fabricList = fabricList;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getSubStyleId() {
        return subStyleId;
    }

    public void setSubStyleId(String subStyleId) {
        this.subStyleId = subStyleId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getProductStyleId() {
        return productStyleId;
    }

    public void setProductStyleId(String productStyleId) {
        this.productStyleId = productStyleId;
    }

    public String getStyleTitle() {
        return styleTitle;
    }

    public void setStyleTitle(String styleTitle) {
        this.styleTitle = styleTitle;
    }

    public String getSubStyleTitle() {
        return subStyleTitle;
    }

    public void setSubStyleTitle(String subStyleTitle) {
        this.subStyleTitle = subStyleTitle;
    }

    public Product() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.parentId);
        dest.writeString(this.categoryId);
        dest.writeString(this.styleId);
        dest.writeString(this.subStyleId);
        dest.writeString(this.productTitle);
        dest.writeString(this.productSubTitle);
        dest.writeString(this.productDetails);
        dest.writeString(this.price);
        dest.writeString(this.calculatedPrice);
        dest.writeString(this.releaseDate);
        dest.writeString(this.expiryDate);
        dest.writeString(this.isActive);
        dest.writeString(this.categoryTitle);
        dest.writeString(this.productStyleId);
        dest.writeString(this.styleTitle);
        dest.writeString(this.subStyleTitle);
        dest.writeString(this.isPriceVariable);
        dest.writeString(this.sequenceNo);
        dest.writeString(this.isGroup);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.fabricList);
    }

    protected Product(Parcel in) {
        this.productId = in.readString();
        this.parentId = in.readString();
        this.categoryId = in.readString();
        this.styleId = in.readString();
        this.subStyleId = in.readString();
        this.productTitle = in.readString();
        this.productSubTitle = in.readString();
        this.productDetails = in.readString();
        this.price = in.readString();
        this.calculatedPrice = in.readString();
        this.releaseDate = in.readString();
        this.expiryDate = in.readString();
        this.isActive = in.readString();
        this.categoryTitle = in.readString();
        this.productStyleId = in.readString();
        this.styleTitle = in.readString();
        this.subStyleTitle = in.readString();
        this.isPriceVariable = in.readString();
        this.sequenceNo = in.readString();
        this.isGroup = in.readString();
        this.checked = in.readByte() != 0;
        this.fabricList = in.createTypedArrayList(Fabric.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
