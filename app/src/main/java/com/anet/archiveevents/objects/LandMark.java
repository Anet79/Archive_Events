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
        double var1 = this.latitude;
        double var3 = this.longitude;
        StringBuilder var5 = new StringBuilder();
        // var5.append("lat/lng: (");
        var5.append(" (");
        var5.append(var1);
        var5.append(",");
        var5.append(var3);
        var5.append(")");
        return var5.toString();
    }
}
