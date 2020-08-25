package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot implements Parcelable {
    @SerializedName("slotId")
    @Expose
    public String slotId;
    @SerializedName("slotTime")
    @Expose
    public String slotTime;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("total")
    @Expose
    public String total;
    @SerializedName("appointmentDate")
    @Expose
    public String appointmentDate;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Slot() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.slotId);
        dest.writeString(this.slotTime);
        dest.writeString(this.isActive);
        dest.writeString(this.total);
        dest.writeString(this.appointmentDate);
    }

    protected Slot(Parcel in) {
        this.slotId = in.readString();
        this.slotTime = in.readString();
        this.isActive = in.readString();
        this.total = in.readString();
        this.appointmentDate = in.readString();
    }

    public static final Creator<Slot> CREATOR = new Creator<Slot>() {
        @Override
        public Slot createFromParcel(Parcel source) {
            return new Slot(source);
        }

        @Override
        public Slot[] newArray(int size) {
            return new Slot[size];
        }
    };
}