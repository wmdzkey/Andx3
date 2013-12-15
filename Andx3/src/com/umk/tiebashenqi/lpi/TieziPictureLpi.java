package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TieziPictureLpi {

    public void saveOrUpdate(Context context, TieziPicture tieziPicture) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdate(tieziPicture);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<TieziPicture> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.saveOrUpdateAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, TieziPicture tieziPicture) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.delete(tieziPicture);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, List<TieziPicture> list) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            albumDb.deleteAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public TieziPicture find(Context context, Long id) {
        DbUtils albumDb = DbUtils.create(context);
        try {
           return albumDb.findById(TieziPicture.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TieziPicture> findAll(Context context) {
        DbUtils albumDb = DbUtils.create(context);
        try {
            return albumDb.findAll(TieziPicture.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

}
