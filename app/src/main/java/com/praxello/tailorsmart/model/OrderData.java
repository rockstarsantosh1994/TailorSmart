package com.praxello.tailorsmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderData {
    @SerializedName("Data")
    @Expose
    public List<Order> data = new ArrayList<>();
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;
    @SerializedName("Message")
    @Expose
    public String message;

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }

    public long getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(long responsecode) {
        this.responsecode = responsecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
