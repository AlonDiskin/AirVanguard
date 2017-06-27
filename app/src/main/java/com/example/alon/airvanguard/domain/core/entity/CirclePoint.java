package com.example.alon.airvanguard.domain.core.entity;

/**
 * Created by alon on 6/24/17.
 */

public class CirclePoint extends MissionPoint {

    private double radius;

    public CirclePoint() {
        super();
    }

    public CirclePoint(int pointPathNum,double lat, double lon, double altitude, double radius) {
        super(pointPathNum,lat, lon, altitude);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
