package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.umk.andx3.lib.config.Code;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="favorite_tiezi")
public class FavoriteTiezi extends IEntity{

    private Long userId;
    private Long tiebaId;
    private Long tieziId;
    private String tieziUrl;

    public Long getTiebaId() {
        return tiebaId;
    }

    public void setTiebaId(Long tiebaId) {
        this.tiebaId = tiebaId;
    }

    public Long getTieziId() {
        return tieziId;
    }

    public void setTieziId(Long tieziId) {
        this.tieziId = tieziId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTieziUrl() {
        return tieziUrl;
    }

    public void setTieziUrl(String tieziUrl) {
        this.tieziUrl = tieziUrl;
    }

}
