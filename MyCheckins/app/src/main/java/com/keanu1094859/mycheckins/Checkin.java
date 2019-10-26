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
    private Double mLongitude;
    private Double mLatitude;

    public Checkin(Double lat, Double lng) {
        this(UUID.randomUUID(), lat, lng);
    }

    public Checkin(UUID id, Double lat, Double lng) {
        mId = id;
        mDate = new Date();
        mLatitude = lat;
        mLongitude = lng;
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

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public String getLocation() {
        return "Latitude: " + mLatitude.toString() +
            "     Longitude: " + mLongitude.toString();
    }
}
