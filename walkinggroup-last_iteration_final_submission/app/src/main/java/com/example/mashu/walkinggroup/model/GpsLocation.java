package com.example.mashu.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The GPSLocation class stores information about a GPS location of a user.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpsLocation {

    private Double lat;
    private Double lng;
    private String timestamp;

    public GpsLocation(){}

    public GpsLocation(Double lat, Double lng, String timestamp) {
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GpsLocation{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}