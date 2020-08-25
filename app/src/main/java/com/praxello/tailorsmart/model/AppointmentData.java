package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentData implements Parcelable {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Data")
    @Expose
    public List<Appointment> data = null;
    @SerializedName("Holiday")
    @Expose
    public List<Holiday> holiday = null;
    @SerializedName("Slots")
    @Expose
    public List<Slot> slots = null;
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;

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

    public List<Appointment> getData() {
        return data;
    }

    public void setData(List<Appointment> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
        dest.writeTypedList(this.holiday);
        dest.writeTypedList(this.slots);
        dest.writeLong(this.responsecode);
    }

    public AppointmentData() {
    }

    protected AppointmentData(Parcel in) {
        this.message = in.readString();
        this.data = in.createTypedArrayList(Appointment.CREATOR);
        this.holiday = in.createTypedArrayList(Holiday.CREATOR);
        this.slots = in.createTypedArrayList(Slot.CREATOR);
        this.responsecode = in.readLong();
    }

    public static final Parcelable.Creator<AppointmentData> CREATOR = new Parcelable.Creator<AppointmentData>() {
        @Override
        public AppointmentData createFromParcel(Parcel source) {
            return new AppointmentData(source);
        }

        @Override
        public AppointmentData[] newArray(int size) {
            return new AppointmentData[size];
        }
    };
}
