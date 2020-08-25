package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorObj implements Parcelable {
    String id, hex, title;
    boolean checked;

    public ColorObj(String id,String hex, String title) {
        this.id = id;
        this.hex = hex;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ColorObj() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.hex);
        dest.writeString(this.title);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected ColorObj(Parcel in) {
        this.id = in.readString();
        this.hex = in.readString();
        this.title = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<ColorObj> CREATOR = new Creator<ColorObj>() {
        @Override
        public ColorObj createFromParcel(Parcel source) {
            return new ColorObj(source);
        }

        @Override
        public ColorObj[] newArray(int size) {
            return new ColorObj[size];
        }
    };
}
