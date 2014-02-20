package com.umk.tiebashenqi.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.umk.andx3.entity.IEntity;
import com.umk.andx3.lib.config.Code;

/**
 * @author Winnid
 * @title 用户
 * @version:1.0
 * @since：14-01-10
 */
@Table(name = "user")
public class User extends IEntity {

    @Id(column = "id")
    private Long id;
    @Column(column = "username")
    private String username;
    @Column(column = "password")
    private String password;
    @Column(column = "sex")
    private Integer sex;
    @Column(column = "the_name")
    private String theName;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
