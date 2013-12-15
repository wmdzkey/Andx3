package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.umk.tiebashenqi.entity.Tieba;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TiebaLpi {

    public void saveOrUpdate(Context context, Tieba tieba) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdate(tieba);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<Tieba> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdateAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, Tieba tieba) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.delete(tieba);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, List<Tieba> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.deleteAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Tieba find(Context context, Long id) {
        DbUtils albumDb = DbUtils.create(context);
        try {
           return albumDb.findById(Tieba.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tieba> findAll(Context context) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            return albumDb.findAll(Tieba.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

}
