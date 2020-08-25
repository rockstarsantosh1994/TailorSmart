package com.praxello.tailorsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Testimonial implements Parcelable {
    @SerializedName("testimonialId")
    @Expose
    public String testimonialId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("wordsBy")
    @Expose
    public String wordsBy;
    @SerializedName("isActive")
    @Expose
    public String isActive;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTestimonialId() {
        return testimonialId;
    }

    public void setTestimonialId(String testimonialId) {
        this.testimonialId = testimonialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWordsBy() {
        return wordsBy;
    }

    public void setWordsBy(String wordsBy) {
        this.wordsBy = wordsBy;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Testimonial() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.testimonialId);
        dest.writeString(this.title);
        dest.writeString(this.details);
        dest.writeString(this.photo);
        dest.writeString(this.wordsBy);
        dest.writeString(this.isActive);
    }

    protected Testimonial(Parcel in) {
        this.testimonialId = in.readString();
        this.title = in.readString();
        this.details = in.readString();
        this.photo = in.readString();
        this.wordsBy = in.readString();
        this.isActive = in.readString();
    }

    public static final Creator<Testimonial> CREATOR = new Creator<Testimonial>() {
        @Override
        public Testimonial createFromParcel(Parcel source) {
            return new Testimonial(source);
        }

        @Override
        public Testimonial[] newArray(int size) {
            return new Testimonial[size];
        }
    };
}
