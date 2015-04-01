package com.example.ninh.notification;

/**
 * Created by ninh on 02/04/2015.
 */
public class ItemTime {
    private int id;
    private int idIntent;
    private double time;
    private double repeat;

    public ItemTime(int id, int idIntent, double time, double repeat) {
        super();
        this.id = id;
        this.idIntent= idIntent;
        this.time = time;
        this.repeat = repeat;
    }


    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setidIntent(int idIntent) {
        this.idIntent = idIntent;
    }

    public int getidIntent() {
        return idIntent;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setRepeat(double repeat) {
        this.repeat = repeat;
    }

    public double getRepeat() {
        return repeat;
    }
}
