package com.aigo.AigoPm25Map.goodairinstall.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangcirui on 16/9/1.
 */
public class ShopInfoBean implements Serializable{

    private String creator;
    private String username;
    private String deviceId;
    private String name;
    private String presentation;
    private String branch;

    private List<String> shop_type;
    private List<String> serviceList;
    private String phone;
    private String open_hours;
    private String close_hours;

    private String latitude;
    private String longitude;
    private String address;
    private String addressDetail;
    private boolean isUpload;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<String> getShop_type() {
        return shop_type;
    }

    public void setShop_type(List<String> shop_type) {
        this.shop_type = shop_type;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getClose_hours() {
        return close_hours;
    }

    public void setClose_hours(String close_hours) {
        this.close_hours = close_hours;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    @Override
    public String toString() {
        return "ShopInfoBean{" +
                "creator='" + creator + '\'' +
                ", username='" + username + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", presentation='" + presentation + '\'' +
                ", branch='" + branch + '\'' +
                ", shop_type=" + shop_type +
                ", serviceList=" + serviceList +
                ", phone='" + phone + '\'' +
                ", open_hours='" + open_hours + '\'' +
                ", close_hours='" + close_hours + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", isUpload=" + isUpload +
                '}';
    }
}
