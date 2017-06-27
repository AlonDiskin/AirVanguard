package com.example.alon.airvanguard.domain.core.entity;

/**
 * Created by alon on 6/24/17.
 */

public class WayPoint extends MissionPoint {

    public WayPoint() {
        super();
    }

    public WayPoint(int pointPathNum,double lat, double lon, double altitude) {
        super(pointPathNum,lat, lon, altitude);
    }
}
