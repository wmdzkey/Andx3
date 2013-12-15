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

}
