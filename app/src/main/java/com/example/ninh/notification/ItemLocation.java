package com.example.ninh.notification;

import android.graphics.drawable.Drawable;

/**
 * Created by ninh on 12/03/2015.
 */
public class ItemLocation {
    private int id;
    private double lat;
    private double lng;

    public ItemLocation(int id, double lat, double lng) {
        super();
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }


    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }
}
