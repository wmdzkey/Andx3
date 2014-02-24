package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;

/**
 * @author Winnid
 * @title 意见反馈
 * @version:1.0
 * @since：14-02-22
 */
@Table(name = "advice")
public class Advice extends IEntity {

    @Id(column = "id")
    private Long id;
    @Column(column = "user_id")
    private Long userId;
    @Column(column = "the_name")
    private String theName;
    @Column(column = "user_name")
    private String userName;
    @Column(column = "user_contact")
    private String userContact;
    @Column(column = "deviceId")
    private String deviceId;
    @Column(column = "deviceName")
    private String deviceName;
    @Column(column = "android_version")
    private String androidVersion;
    @Column(column = "app_version")
    private String appVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
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
