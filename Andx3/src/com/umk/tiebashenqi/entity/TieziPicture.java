package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "tiezi_picture")
public class TieziPicture {

    @Id
    private Long id;
    @Column(column = "tieba_id")
    private Long tiebaId;
    @Column(column = "tiezi_id")
    private Long tieziId;
    @Column(column = "image_url")
    private String imageUrl;
    @Column(column = "state")
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
