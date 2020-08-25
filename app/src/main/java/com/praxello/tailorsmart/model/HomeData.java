package com.praxello.tailorsmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeData {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("CurrentOffers")
    @Expose
    public List<CurrentOffer> currentOfferList = null;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;

    public List<CurrentOffer> getCurrentOfferList() {
        return currentOfferList;
    }

    public void setCurrentOfferList(List<CurrentOffer> currentOfferList) {
        this.currentOfferList = currentOfferList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(long responsecode) {
        this.responsecode = responsecode;
    }
}
