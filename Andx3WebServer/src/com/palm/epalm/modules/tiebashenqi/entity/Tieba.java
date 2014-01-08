package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.palm.epalm.modules.tiebashenqi.config.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tieba")
public class Tieba extends IEntity{

    @Column(length = 255)
    private String theName;
    private String theNameUrl;
    private Integer state = Code.State.Normal;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
