package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse implements Parcelable {
    @SerializedName("Responsecode")
    @Expose
    public long responsecode;
    @SerializedName("Message")
    @Expose
    public String message;

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

    public UserResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.responsecode);
        dest.writeString(this.message);
    }

    protected UserResponse(Parcel in) {
        this.responsecode = in.readLong();
        this.message = in.readString();
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel source) {
            return new UserResponse(source);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };
}
