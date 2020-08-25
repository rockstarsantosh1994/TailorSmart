package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentDetails implements Parcelable {
    @SerializedName("appointmentId")
    @Expose
    public String appointmentId;
    @SerializedName("customerId")
    @Expose
    public String customerId;
    @SerializedName("employeename")
    @Expose
    public String employeename;
    @SerializedName("productIds")
    @Expose
    public String productIds;
    @SerializedName("fabricIds")
    @Expose
    public String fabricIds;
    @SerializedName("appointmentDate")
    @Expose
    public String appointmentDate;
    @SerializedName("slotId")
    @Expose
    public String slotId;
    @SerializedName("servingEmployeeId")
    @Expose
    public String servingEmployeeId;
    @SerializedName("appointmentStatus")
    @Expose
    public String appointmentStatus;
    @SerializedName("entryDateTime")
    @Expose
    public String entryDateTime;
    @SerializedName("slotTime")
    @Expose
    public String slotTime;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("date_birth")
    @Expose
    public String dateBirth;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("landline")
    @Expose
    public String landline;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("isMale")
    @Expose
    public String isMale;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("issocial")
    @Expose
    public String issocial;

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getFabricIds() {
        return fabricIds;
    }

    public void setFabricIds(String fabricIds) {
        this.fabricIds = fabricIds;
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

    public String getServingEmployeeId() {
        return servingEmployeeId;
    }

    public void setServingEmployeeId(String servingEmployeeId) {
        this.servingEmployeeId = servingEmployeeId;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(String entryDateTime) {
        this.entryDateTime = entryDateTime;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsMale() {
        return isMale;
    }

    public void setIsMale(String isMale) {
        this.isMale = isMale;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getIssocial() {
        return issocial;
    }

    public void setIssocial(String issocial) {
        this.issocial = issocial;
    }

    public AppointmentDetails() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appointmentId);
        dest.writeString(this.customerId);
        dest.writeString(this.employeename);
        dest.writeString(this.productIds);
        dest.writeString(this.fabricIds);
        dest.writeString(this.appointmentDate);
        dest.writeString(this.slotId);
        dest.writeString(this.servingEmployeeId);
        dest.writeString(this.appointmentStatus);
        dest.writeString(this.entryDateTime);
        dest.writeString(this.slotTime);
        dest.writeString(this.isActive);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.dateBirth);
        dest.writeString(this.mobile);
        dest.writeString(this.landline);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.address);
        dest.writeString(this.isMale);
        dest.writeString(this.password);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.landmark);
        dest.writeString(this.issocial);
    }

    protected AppointmentDetails(Parcel in) {
        this.appointmentId = in.readString();
        this.customerId = in.readString();
        this.employeename = in.readString();
        this.productIds = in.readString();
        this.fabricIds = in.readString();
        this.appointmentDate = in.readString();
        this.slotId = in.readString();
        this.servingEmployeeId = in.readString();
        this.appointmentStatus = in.readString();
        this.entryDateTime = in.readString();
        this.slotTime = in.readString();
        this.isActive = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.dateBirth = in.readString();
        this.mobile = in.readString();
        this.landline = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.address = in.readString();
        this.isMale = in.readString();
        this.password = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.landmark = in.readString();
        this.issocial = in.readString();
    }

    public static final Creator<AppointmentDetails> CREATOR = new Creator<AppointmentDetails>() {
        @Override
        public AppointmentDetails createFromParcel(Parcel source) {
            return new AppointmentDetails(source);
        }

        @Override
        public AppointmentDetails[] newArray(int size) {
            return new AppointmentDetails[size];
        }
    };
}