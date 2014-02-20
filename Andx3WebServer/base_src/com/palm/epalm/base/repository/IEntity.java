package com.palm.epalm.base.repository;

import com.umk.andx3.lib.config.Code;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Date: 13-3-19
 * Time: 下午12:51
 * -基础实体类，提供默认ID字段，可通过继承该类一减少Id字段的声明，同时也是一个类型限定
 *
 * @author desire
 * @version 1.0
 */
@MappedSuperclass
public abstract class IEntity implements Serializable {

    protected Long id;
    protected Integer state = Code.State.Normal;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS")
    protected Date createTime = new Date();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS")
    protected Date updateTime;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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
