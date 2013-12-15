package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TieziLpi {

    public void saveOrUpdate(Context context, Tiezi tiezi) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdate(tiezi);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<Tiezi> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdateAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, Tiezi tiezi) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.delete(tiezi);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, List<Tiezi> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.deleteAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Tiezi find(Context context, Long id) {
        DbUtils albumDb = DbUtils.create(context);
        try {
           return albumDb.findById(Tiezi.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tiezi> findAll(Context context) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            return albumDb.findAll(Tiezi.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

}
