package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @author Winnid
 * @title 贴吧数据贴
 * @version:1.0
 * @since：13-12-15
 */
@Table(name = "tieba")
public class Tieba {

    @Id
    private Long id;
    @Column(column = "the_name")
    private String theName;
    @Column(column = "the_name_url")
    private String theNameUrl;
    @Column(column = "state")
    private Integer state;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getTheNameUrl() {
        return theNameUrl;
    }

    public void setTheNameUrl(String theNameUrl) {
        this.theNameUrl = theNameUrl;
    }
}
