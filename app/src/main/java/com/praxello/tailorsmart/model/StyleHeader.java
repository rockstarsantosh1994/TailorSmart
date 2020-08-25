package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;


import com.praxello.tailorsmart.widget.expandablerecyclerview.model.Parent;

import java.util.List;

public class StyleHeader implements Parcelable, Parent<StyleOrderItem> {
    public String title;
    public List<StyleOrderItem> styleOrderItemList = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StyleOrderItem> getStyleOrderItemList() {
        return styleOrderItemList;
    }

    public void setStyleOrderItemList(List<StyleOrderItem> styleOrderItemList) {
        this.styleOrderItemList = styleOrderItemList;
    }

    @Override
    public List<StyleOrderItem> getChildList() {
        return getStyleOrderItemList();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeTypedList(this.styleOrderItemList);
    }

    public StyleHeader() {
    }

    protected StyleHeader(Parcel in) {
        this.title = in.readString();
        this.styleOrderItemList = in.createTypedArrayList(StyleOrderItem.CREATOR);
    }

    public static final Creator<StyleHeader> CREATOR = new Creator<StyleHeader>() {
        @Override
        public StyleHeader createFromParcel(Parcel source) {
            return new StyleHeader(source);
        }

        @Override
        public StyleHeader[] newArray(int size) {
            return new StyleHeader[size];
        }
    };
}
