package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;

/**
 * @author Winnid
 * @title 贴吧帖子数据贴
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "tiezi")
public class Tiezi extends IEntity {

    @Id(column = "id")
    private Long id;
    @Column(column = "the_name")
    private String theName;
    @Column(column = "url")
    private String url;
    @Column(column = "tieba_id")
    private Long tiebaId;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
