package com.example.alon.airvanguard.presentation.entity;

import java.io.Serializable;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;

/**
 * {@link DistressCall} data transfer object.
 */

public class DistressCallDTO implements Serializable {

    private String userName;
    private String userPhotoUrl;
    private double lat;
    private double lon;
    private long timeStamp;

    public DistressCallDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
