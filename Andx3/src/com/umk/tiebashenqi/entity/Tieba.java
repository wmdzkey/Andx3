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
    @Column(column = "state")
    private Integer state;

}
