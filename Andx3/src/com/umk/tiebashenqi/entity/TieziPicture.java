package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "tiezi_picture")
public class TieziPicture extends IEntity {

    @Id(column = "id")
    private Long id;

    @Column(column = "the_name")
    private String theName;
    @Column(column = "tieba_id")
    private Long tiebaId;
    @Column(column = "tiezi_id")
    private Long tieziId;
    @Column(column = "image_url")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
