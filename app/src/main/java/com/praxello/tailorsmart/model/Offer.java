package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Offer implements Parcelable {
    String id, title;
    boolean checked;

    public Offer(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Offer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected Offer(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
