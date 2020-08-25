package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency implements Parcelable {
    @SerializedName("cityName")
    @Expose
    public String cityName;
    @SerializedName("currencyMultiplier")
    @Expose
    public String currencyMultiplier;
    @SerializedName("currencyCode")
    @Expose
    public String currencyCode;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrencyMultiplier() {
        return currencyMultiplier;
    }

    public void setCurrencyMultiplier(String currencyMultiplier) {
        this.currencyMultiplier = currencyMultiplier;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeString(this.currencyMultiplier);
        dest.writeString(this.currencyCode);
    }

    public Currency() {
    }

    protected Currency(Parcel in) {
        this.cityName = in.readString();
        this.currencyMultiplier = in.readString();
        this.currencyCode = in.readString();
    }

    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}