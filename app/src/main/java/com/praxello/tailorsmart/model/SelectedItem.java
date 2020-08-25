package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedItem implements Parcelable {

    @SerializedName("Product")
    @Expose
    public Product product;
    @SerializedName("Fabrics")
    @Expose
    public List<Fabric> fabrics = null;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Fabric> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Fabric> fabrics) {
        this.fabrics = fabrics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, flags);
        dest.writeTypedList(this.fabrics);
    }

    public SelectedItem() {
    }

    protected SelectedItem(Parcel in) {
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.fabrics = in.createTypedArrayList(Fabric.CREATOR);
    }

    public static final Parcelable.Creator<SelectedItem> CREATOR = new Parcelable.Creator<SelectedItem>() {
        @Override
        public SelectedItem createFromParcel(Parcel source) {
            return new SelectedItem(source);
        }

        @Override
        public SelectedItem[] newArray(int size) {
            return new SelectedItem[size];
        }
    };
}