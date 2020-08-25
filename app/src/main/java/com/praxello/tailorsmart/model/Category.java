package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    String id, title;
    boolean checked;

    public Category(String id, String title) {
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

    public Category() {
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

    protected Category(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
