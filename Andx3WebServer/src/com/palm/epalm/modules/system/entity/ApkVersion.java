package com.palm.epalm.modules.system.entity;

import com.palm.epalm.base.repository.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Apk版本信息
 */
@Entity
@Table(name = "apk_version")
public class ApkVersion extends IEntity {

    /**
     * 对应android中versionCode
      */
    @Column(name = "version_code", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    private String versionCode;
    /**
     * 更新信息。
     */
    @Column(name = "version_info", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    private String versionInfo;

    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getVersionInfo() {
        return versionInfo;
    }
    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

}
