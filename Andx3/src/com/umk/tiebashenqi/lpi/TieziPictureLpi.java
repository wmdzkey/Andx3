package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.base.BaseLpi;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TieziPictureLpi extends BaseLpi<TieziPicture> {

    public void saveOrUpdate(Context context, TieziPicture tieziPicture) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            if(exist(context, tieziPicture) == null) {
                dbUtils.saveOrUpdate(tieziPicture);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<TieziPicture> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            for(TieziPicture tieziPicture : list) {
                if(exist(context, tieziPicture) == null) {
                    dbUtils.saveOrUpdate(tieziPicture);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public TieziPicture exist(Context context, TieziPicture tieziPicture) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            TieziPicture tieziPictureInDB = dbUtils.findFirst(TieziPicture.class, WhereBuilder.b().and("url", "=", tieziPicture.getImageUrl()));
            if(tieziPictureInDB != null) {
                return tieziPictureInDB;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TieziPicture> findByPage(Context context, int pageNo, int pageSize) {
        DbUtils dbUtils = getDbUtils(context);
        List<TieziPicture> tieziPictureList = null;
        try {
            tieziPictureList = dbUtils.findAll(Selector.from(TieziPicture.class).orderBy("id", false).limit(pageSize).offset(pageSize * pageNo));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return tieziPictureList;
    }
}
