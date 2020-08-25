package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Appointment implements Parcelable {
    @SerializedName("AppointmentDetails")
    @Expose
    public AppointmentDetails appointmentDetails;
    @SerializedName("SelectedItems")
    @Expose
    public List<SelectedItem> selectedItems = null;

    public AppointmentDetails getAppointmentDetails() {
        return appointmentDetails;
    }

    public void setAppointmentDetails(AppointmentDetails appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
    }

    public List<SelectedItem> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<SelectedItem> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public Appointment() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.appointmentDetails, flags);
        dest.writeTypedList(this.selectedItems);
    }

    protected Appointment(Parcel in) {
        this.appointmentDetails = in.readParcelable(AppointmentDetails.class.getClassLoader());
        this.selectedItems = in.createTypedArrayList(SelectedItem.CREATOR);
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
