package com.keanu1094859.mycheckins;

import android.net.Uri;

import java.util.Date;
import java.util.UUID;

public class Checkin {
    private UUID mId;
    private String mTitle;
    private String mPlace;
    private String mDetails;
    private Date mDate;
    private Uri mImage;
    private Float mLongitude;
    private Float mLatitude;

    public Checkin() {
        this(UUID.randomUUID());
    }

    public Checkin(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri imageUri) {
        mImage = imageUri;
    }

    public Float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Float longitude) {
        mLongitude = longitude;
    }

    public Float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Float latitude) {
        mLatitude = latitude;
    }
}
