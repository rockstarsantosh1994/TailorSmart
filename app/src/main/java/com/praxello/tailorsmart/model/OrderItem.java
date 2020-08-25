package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderItem implements Parcelable {
    @SerializedName("OrderItem")
    @Expose
    public ProductOrderItem productOrderItem;
    @SerializedName("Fabrics")
    @Expose
    public List<FabricOrderItem> fabrics = null;
    @SerializedName("Measurements")
    @Expose
    public List<Measurement> measurements = null;
    @SerializedName("Styles")
    @Expose
    public List<StyleOrderItem> styles = null;

    public ProductOrderItem getProductOrderItem() {
        return productOrderItem;
    }

    public void setProductOrderItem(ProductOrderItem productOrderItem) {
        this.productOrderItem = productOrderItem;
    }

    public List<FabricOrderItem> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<FabricOrderItem> fabrics) {
        this.fabrics = fabrics;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public List<StyleOrderItem> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleOrderItem> styles) {
        this.styles = styles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.productOrderItem, flags);
        dest.writeTypedList(this.fabrics);
        dest.writeTypedList(this.measurements);
        dest.writeTypedList(this.styles);
    }

    public OrderItem() {
    }

    protected OrderItem(Parcel in) {
        this.productOrderItem = in.readParcelable(ProductOrderItem.class.getClassLoader());
        this.fabrics = in.createTypedArrayList(FabricOrderItem.CREATOR);
        this.measurements = in.createTypedArrayList(Measurement.CREATOR);
        this.styles = in.createTypedArrayList(StyleOrderItem.CREATOR);
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}