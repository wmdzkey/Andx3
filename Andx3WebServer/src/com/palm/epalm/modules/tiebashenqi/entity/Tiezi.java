package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.umk.andx3.lib.config.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tiezi")
public class Tiezi extends IEntity{

    @Column(length = 255)
    private String theName;
    private String url;
    private Long tiebaId;

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTiebaId() {
        return tiebaId;
    }

    public void setTiebaId(Long tiebaId) {
        this.tiebaId = tiebaId;
    }
}
