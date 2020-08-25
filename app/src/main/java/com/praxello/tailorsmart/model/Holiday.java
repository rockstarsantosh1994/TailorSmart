package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Holiday implements Parcelable {

    @SerializedName("skipDate")
    @Expose
    public String skipDate;
    @SerializedName("holidayTitle")
    @Expose
    public String holidayTitle;

    public String getSkipDate() {
        return skipDate;
    }

    public void setSkipDate(String skipDate) {
        this.skipDate = skipDate;
    }

    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.skipDate);
        dest.writeString(this.holidayTitle);
    }

    public Holiday() {
    }

    protected Holiday(Parcel in) {
        this.skipDate = in.readString();
        this.holidayTitle = in.readString();
    }

    public static final Parcelable.Creator<Holiday> CREATOR = new Parcelable.Creator<Holiday>() {
        @Override
        public Holiday createFromParcel(Parcel source) {
            return new Holiday(source);
        }

        @Override
        public Holiday[] newArray(int size) {
            return new Holiday[size];
        }
    };
}