package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOrderItem implements Parcelable {
    @SerializedName("orderItemId")
    @Expose
    public String orderItemId;
    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("productId")
    @Expose
    public String productId;
    @SerializedName("orderItemPrice")
    @Expose
    public String orderItemPrice;
    @SerializedName("creationDateTime")
    @Expose
    public String creationDateTime;
    @SerializedName("parentId")
    @Expose
    public String parentId;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
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
    @SerializedName("skuNo")
    @Expose
    public String skuNo;
    @SerializedName("releaseDate")
    @Expose
    public String releaseDate;
    @SerializedName("ownerId")
    @Expose
    public String ownerId;
    @SerializedName("sequenceNo")
    @Expose
    public String sequenceNo;
    @SerializedName("isPriceVariable")
    @Expose
    public String isPriceVariable;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("deliveryDate")
    @Expose
    public String deliveryDate;
    boolean checked = false;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(String orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getIsPriceVariable() {
        return isPriceVariable;
    }

    public void setIsPriceVariable(String isPriceVariable) {
        this.isPriceVariable = isPriceVariable;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public ProductOrderItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderItemId);
        dest.writeString(this.orderId);
        dest.writeString(this.productId);
        dest.writeString(this.orderItemPrice);
        dest.writeString(this.creationDateTime);
        dest.writeString(this.parentId);
        dest.writeString(this.categoryId);
        dest.writeString(this.productTitle);
        dest.writeString(this.productSubTitle);
        dest.writeString(this.productDetails);
        dest.writeString(this.price);
        dest.writeString(this.skuNo);
        dest.writeString(this.releaseDate);
        dest.writeString(this.ownerId);
        dest.writeString(this.sequenceNo);
        dest.writeString(this.isPriceVariable);
        dest.writeString(this.isActive);
        dest.writeString(this.deliveryDate);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected ProductOrderItem(Parcel in) {
        this.orderItemId = in.readString();
        this.orderId = in.readString();
        this.productId = in.readString();
        this.orderItemPrice = in.readString();
        this.creationDateTime = in.readString();
        this.parentId = in.readString();
        this.categoryId = in.readString();
        this.productTitle = in.readString();
        this.productSubTitle = in.readString();
        this.productDetails = in.readString();
        this.price = in.readString();
        this.skuNo = in.readString();
        this.releaseDate = in.readString();
        this.ownerId = in.readString();
        this.sequenceNo = in.readString();
        this.isPriceVariable = in.readString();
        this.isActive = in.readString();
        this.deliveryDate = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<ProductOrderItem> CREATOR = new Creator<ProductOrderItem>() {
        @Override
        public ProductOrderItem createFromParcel(Parcel source) {
            return new ProductOrderItem(source);
        }

        @Override
        public ProductOrderItem[] newArray(int size) {
            return new ProductOrderItem[size];
        }
    };
}
