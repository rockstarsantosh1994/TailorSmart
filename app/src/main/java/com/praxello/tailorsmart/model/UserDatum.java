package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Parcelable {
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("userType")
    @Expose
    public String userType;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("adharId")
    @Expose
    public String adharId;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("birthDate")
    @Expose
    public String birthDate;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAdharId() {
        return adharId;
    }

    public void setAdharId(String adharId) {
        this.adharId = adharId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userType);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.adharId);
        dest.writeString(this.address);
        dest.writeString(this.password);
        dest.writeString(this.birthDate);
        dest.writeString(this.isActive);
    }

    public UserDatum() {
    }

    protected UserDatum(Parcel in) {
        this.userId = in.readString();
        this.userType = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.adharId = in.readString();
        this.address = in.readString();
        this.password = in.readString();
        this.birthDate = in.readString();
        this.isActive = in.readString();
    }

    public static final Parcelable.Creator<UserDatum> CREATOR = new Parcelable.Creator<UserDatum>() {
        @Override
        public UserDatum createFromParcel(Parcel source) {
            return new UserDatum(source);
        }

        @Override
        public UserDatum[] newArray(int size) {
            return new UserDatum[size];
        }
    };
}