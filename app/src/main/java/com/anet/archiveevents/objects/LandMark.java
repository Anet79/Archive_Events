package com.anet.archiveevents.objects;

public class LandMark {

    private double latitude;
    private double longitude;


    public LandMark() {
    }

    public LandMark(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "{" +
                "" + latitude +
                "\t,\t" + longitude +
                '}';
    }
}
