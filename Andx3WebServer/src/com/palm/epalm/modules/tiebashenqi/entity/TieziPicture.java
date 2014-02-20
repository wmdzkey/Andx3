package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.umk.andx3.lib.config.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tiezi_picture")
public class TieziPicture extends IEntity{

    @Column(length = 255)
    private String theName;
    private Long tiebaId;
    private Long tieziId;
    private String imageUrl;

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
