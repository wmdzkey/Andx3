package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.base.BaseLpi;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

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

}
