package com.example.alon.airvanguard.domain.core.entity;

import java.io.Serializable;

public abstract class MissionPoint implements Serializable {

    private int pointPathNum;
    private double lat;
    private double lon;
    private double altitude;

    public MissionPoint() {
    }

    public MissionPoint(int pointPathNum,double lat, double lon,double altitude) {
        this.pointPathNum = pointPathNum;
        this.lat = lat;
        this.lon = lon;
        this.altitude = altitude;
    }

    public int getPointPathNum() {
        return pointPathNum;
    }

    public void setPointPathNum(int pointPathNum) {
        this.pointPathNum = pointPathNum;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
