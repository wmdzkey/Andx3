package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;
import com.umk.andx3.lib.config.Code;

/**
 * @author Winnid
 * @title 贴吧数据贴
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "tieba")
public class Tieba extends IEntity {

    @Id(column = "id")
    @NoAutoIncrement
    private Long id;
    @Column(column = "the_name")
    private String theName;
    @Column(column = "the_name_url")
    private String theNameUrl;
    @Column(column = "logo_url")
    private String logoUrl;

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
