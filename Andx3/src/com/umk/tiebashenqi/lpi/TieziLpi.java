package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.base.BaseLpi;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TieziLpi extends BaseLpi<Tiezi> {

    public void saveOrUpdate(Context context, Tiezi tiezi) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            if(exist(context, tiezi) == null) {
                dbUtils.saveOrUpdate(tiezi);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<Tiezi> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            for(Tiezi tiezi : list) {
                if(exist(context, tiezi) == null) {
                    dbUtils.saveOrUpdate(tiezi);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Tiezi exist(Context context, Tiezi tiezi) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            Tiezi tieziInDB = dbUtils.findFirst(Tiezi.class, WhereBuilder.b().and("url","=",tiezi.getUrl()));
            if(tieziInDB != null) {
                return tieziInDB;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
