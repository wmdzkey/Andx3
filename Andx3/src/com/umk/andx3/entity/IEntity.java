package com.umk.andx3.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.umk.andx3.lib.config.Code;

import java.io.Serializable;
import java.util.Date;

/**
 * SQLite基本类
 */
public class IEntity implements Serializable {

    public Integer state = Code.state.Normal;
    public Date createTime = new Date();
    public Date updateTime;


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
