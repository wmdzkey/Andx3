package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;

/**
 * @author Winnid
 * @title 贴吧帖子数据贴
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "favorite_tiezi")
public class FavoriteTiezi extends IEntity {

    @Id(column = "id")
    private Long id;
    @Column(column = "user_id")
    private Long userId;
    @Column(column = "tieba_id")
    private Long tiebaId;
    @Column(column = "tiezi_url")
    private String tieziUrl;

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

    public Long getTiebaId() {
        return tiebaId;
    }

    public void setTiebaId(Long tiebaId) {
        this.tiebaId = tiebaId;
    }

    public String getTieziUrl() {
        return tieziUrl;
    }

    public void setTieziUrl(String tieziUrl) {
        this.tieziUrl = tieziUrl;
    }

}
