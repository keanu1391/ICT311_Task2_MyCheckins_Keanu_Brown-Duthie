package com.keanu1094859.mycheckins;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

public class Checkin {
    private UUID mId;
    private String mTitle;
    private String mPlace;
    private String mDetails;
    private Date mDate;
    private Double mLongitude;
    private Double mLatitude;

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

    public String getFormattedDate(String dateFormat) {
        if (dateFormat == null) {
            dateFormat = "EEE, d MMM yyyy";
        }

        String dateString = DateFormat.format(dateFormat, mDate).toString();

        return dateString;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
