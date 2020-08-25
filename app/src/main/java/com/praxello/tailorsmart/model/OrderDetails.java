package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails implements Parcelable {
    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("customerId")
    @Expose
    public String customerId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("purchaseDateTime")
    @Expose
    public String purchaseDateTime;
    @SerializedName("promoCode")
    @Expose
    public String promoCode;
    @SerializedName("isSucceed")
    @Expose
    public String isSucceed;
    @SerializedName("orderStatus")
    @Expose
    public String orderStatus;
    @SerializedName("employeeId")
    @Expose
    public String employeeId;
    @SerializedName("isConfirmed")
    @Expose
    public String isConfirmed;
    @SerializedName("paymentValue")
    @Expose
    public String paymentValue;
    @SerializedName("paymentDateTime")
    @Expose
    public String paymentDateTime;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(String purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(String paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public OrderDetails() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.customerId);
        dest.writeString(this.amount);
        dest.writeString(this.currency);
        dest.writeString(this.purchaseDateTime);
        dest.writeString(this.promoCode);
        dest.writeString(this.isSucceed);
        dest.writeString(this.orderStatus);
        dest.writeString(this.employeeId);
        dest.writeString(this.isConfirmed);
        dest.writeString(this.paymentValue);
        dest.writeString(this.paymentDateTime);
    }

    protected OrderDetails(Parcel in) {
        this.orderId = in.readString();
        this.customerId = in.readString();
        this.amount = in.readString();
        this.currency = in.readString();
        this.purchaseDateTime = in.readString();
        this.promoCode = in.readString();
        this.isSucceed = in.readString();
        this.orderStatus = in.readString();
        this.employeeId = in.readString();
        this.isConfirmed = in.readString();
        this.paymentValue = in.readString();
        this.paymentDateTime = in.readString();
    }

    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        @Override
        public OrderDetails createFromParcel(Parcel source) {
            return new OrderDetails(source);
        }

        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
}