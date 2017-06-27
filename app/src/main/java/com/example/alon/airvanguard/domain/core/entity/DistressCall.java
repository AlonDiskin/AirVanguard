package com.example.alon.airvanguard.domain.core.entity;

/**
 * Domain entity class holding a distress call info.
 */

public class DistressCall {

    private User user;
    private Location location;
    private long timeStamp;

    public DistressCall() {
    }

    public DistressCall(User user, Location location, long timeStamp) {
        this.user = user;
        this.location = location;
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Distress call from:" + user.getName() + ", lat: "
                + this.location.getLat() + ", lon: " + this.location.getLon();
    }
}
