package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {
    @SerializedName("customerId")
    @Expose
    public String customerId;
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
    @SerializedName("photoUrl")
    @Expose
    public String photoUrl;
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
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("issocial")
    @Expose
    public String issocial;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsSocial() {
        return issocial;
    }

    public void setIsSocial(String issocial) {
        this.issocial = issocial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerId);
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
        dest.writeString(this.photoUrl);
        dest.writeString(this.password);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.landmark);
        dest.writeString(this.isActive);
        dest.writeString(this.issocial);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.customerId = in.readString();
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
        this.photoUrl = in.readString();
        this.password = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.landmark = in.readString();
        this.isActive = in.readString();
        this.issocial = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}