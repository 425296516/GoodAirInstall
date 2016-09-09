package com.aigo.AigoPm25Map.goodairinstall;

import java.io.Serializable;

/**
 * Created by zhangcirui on 16/8/31.
 */
public class LookRouteObject implements Serializable {

    private String name;
    private String address;
    private float longitude;
    private float latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LookRouteObject{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
