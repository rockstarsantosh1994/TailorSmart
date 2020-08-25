package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.stetho.json.annotation.JsonValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class Payment implements Parcelable {
    @SerializedName("paymentId")
    @Expose
    public String paymentId;
    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("paymentType")
    @Expose
    public String paymentType;
    @SerializedName("paymentMode")
    @Expose
    public String paymentMode;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("isSuceed")
    @Expose
    public String isSuceed;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("paymentDateTime")
    @Expose
    public String paymentDateTime;
    @SerializedName("isDeleted")
    @Expose
    public String isDeleted;
    @SerializedName("deletedBy")
    @Expose
    public String deletedBy;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIsSuceed() {
        return isSuceed;
    }

    public void setIsSuceed(String isSuceed) {
        this.isSuceed = isSuceed;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentId);
        dest.writeString(this.orderId);
        dest.writeString(this.paymentType);
        dest.writeString(this.paymentMode);
        dest.writeString(this.amount);
        dest.writeString(this.currency);
        dest.writeString(this.isSuceed);
        dest.writeString(this.createdBy);
        dest.writeString(this.paymentDateTime);
        dest.writeString(this.isDeleted);
        dest.writeString(this.deletedBy);
    }

    public Payment() {
    }

    protected Payment(Parcel in) {
        this.paymentId = in.readString();
        this.orderId = in.readString();
        this.paymentType = in.readString();
        this.paymentMode = in.readString();
        this.amount = in.readString();
        this.currency = in.readString();
        this.isSuceed = in.readString();
        this.createdBy = in.readString();
        this.paymentDateTime = in.readString();
        this.isDeleted = in.readString();
        this.deletedBy = in.readString();
    }

    public static final Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel source) {
            return new Payment(source);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
}
