package com.course.myapplication.travel;

public class Voyage {
    public static final int SEASIDE = 0;
    public static final int CITY_BREAK = 1;
    public static final int MOUNTAINS = 2;

    private String mTripName;

    public String getTripName() {
        return mTripName;
    }

    public void setTripName(String tripName) {
        mTripName = tripName;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getTripType() {
        return mTripType;
    }

    public void setTripType(int tripType) {
        mTripType = tripType;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    private String mDestination;
    private int mPrice;
    private int mTripType;
    private float mRating;
    private int mImageResource;


}
