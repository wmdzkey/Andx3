package com.palm.epalm.modules.tiebashenqi.vo;

import com.palm.epalm.base.repository.IEntity;
import com.palm.epalm.modules.tiebashenqi.config.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class TiebaVO extends IEntity {

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
