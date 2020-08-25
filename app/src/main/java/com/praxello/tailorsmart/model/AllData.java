package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllData implements Parcelable {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Currency")
    @Expose
    public List<Currency> currencyList = null;
    @SerializedName("Holiday")
    @Expose
    public List<Holiday> holiday = null;
    @SerializedName("Slots")
    @Expose
    public List<Slot> slots = null;
    @SerializedName("Data")
    @Expose
    public ArrayList<Product> data = null;
    @SerializedName("Fabrics")
    @Expose
    public List<Fabric> fabrics = null;
    @SerializedName("Testimonial")
    @Expose
    public List<Testimonial> testimonialList = null;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<Testimonial> getTestimonialList() {
        return testimonialList;
    }

    public void setTestimonialList(List<Testimonial> testimonialList) {
        this.testimonialList = testimonialList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Holiday> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<Holiday> holiday) {
        this.holiday = holiday;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }

    public List<Fabric> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Fabric> fabrics) {
        this.fabrics = fabrics;
    }

    public long getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(long responsecode) {
        this.responsecode = responsecode;
    }

    public AllData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeTypedList(this.currencyList);
        dest.writeTypedList(this.holiday);
        dest.writeTypedList(this.slots);
        dest.writeTypedList(this.data);
        dest.writeTypedList(this.fabrics);
        dest.writeTypedList(this.testimonialList);
        dest.writeLong(this.responsecode);
    }

    protected AllData(Parcel in) {
        this.message = in.readString();
        this.currencyList = in.createTypedArrayList(Currency.CREATOR);
        this.holiday = in.createTypedArrayList(Holiday.CREATOR);
        this.slots = in.createTypedArrayList(Slot.CREATOR);
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.fabrics = in.createTypedArrayList(Fabric.CREATOR);
        this.testimonialList = in.createTypedArrayList(Testimonial.CREATOR);
        this.responsecode = in.readLong();
    }

    public static final Creator<AllData> CREATOR = new Creator<AllData>() {
        @Override
        public AllData createFromParcel(Parcel source) {
            return new AllData(source);
        }

        @Override
        public AllData[] newArray(int size) {
            return new AllData[size];
        }
    };
}
