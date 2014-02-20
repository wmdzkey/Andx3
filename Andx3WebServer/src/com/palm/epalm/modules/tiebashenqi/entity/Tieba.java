package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.umk.andx3.lib.config.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tieba")
public class Tieba extends IEntity{

    @Column(length = 1024)
    private String theName;
    @Column(length = 1024)
    private String theNameUrl;
    @Column(length = 1024)
    private String logoUrl;

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }

    public String getTheNameUrl() {
        return theNameUrl;
    }

    public void setTheNameUrl(String theNameUrl) {
        this.theNameUrl = theNameUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
