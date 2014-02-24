package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="advice")
public class Advice extends IEntity{

    private Long userId;
    @Column(length = 1024)
    private String theName;
    @Column(length = 1024)
    private String userName;
    @Column(length = 1024)
    private String userContact;
    @Column(length = 1024)
    private String deviceId;
    @Column(length = 1024)
    private String deviceName;
    @Column(length = 1024)
    private String androidVersion;
    @Column(length = 1024)
    private String appVersion;


    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
