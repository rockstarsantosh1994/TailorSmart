package com.praxello.tailorsmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VacancyData {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Data")
    @Expose
    public List<Slot> data = null;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Slot> getData() {
        return data;
    }

    public void setData(List<Slot> data) {
        this.data = data;
    }

    public long getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(long responsecode) {
        this.responsecode = responsecode;
    }
}
