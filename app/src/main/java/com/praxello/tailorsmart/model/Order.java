package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Order implements Parcelable {
    @SerializedName("OrderDetails")
    @Expose
    public OrderDetails orderDetails;
    @SerializedName("orderItems")
    @Expose
    public List<OrderItem> orderItems;
    @SerializedName("Payments")
    @Expose
    public List<Payment> paymentList;

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public List<Payment> getIsSucceedPaymentList() {
        List<Payment> payments = new ArrayList<>();
        if (paymentList != null && paymentList.size() > 0) {
            for (int i = 0; i < paymentList.size(); i++) {
                if (paymentList.get(i).getIsSuceed().equals("0")) {
                    payments.add(paymentList.get(i));
                }
            }
        }
        return payments;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.orderDetails, flags);
        dest.writeTypedList(this.orderItems);
        dest.writeTypedList(this.paymentList);
    }

    protected Order(Parcel in) {
        this.orderDetails = in.readParcelable(OrderDetails.class.getClassLoader());
        this.orderItems = in.createTypedArrayList(OrderItem.CREATOR);
        this.paymentList = in.createTypedArrayList(Payment.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
