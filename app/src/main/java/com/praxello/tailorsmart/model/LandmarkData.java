package com.praxello.tailorsmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandmarkData {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Data")
    @Expose
    public List<Landmark> data = null;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;

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

    public List<Landmark> getData() {
        return data;
    }

    public void setData(List<Landmark> data) {
        this.data = data;
    }
}
