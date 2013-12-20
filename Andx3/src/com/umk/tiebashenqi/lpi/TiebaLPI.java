package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.base.BaseLpi;
import com.umk.tiebashenqi.entity.Tieba;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class TiebaLpi extends BaseLpi<Tieba> {

    public void saveOrUpdate(Context context, Tieba tieba) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            Tieba tiebaInDB = exist(context, tieba);
            if(tiebaInDB == null) {
                dbUtils.saveOrUpdate(tieba);
            } else {
                tieba.setId(tiebaInDB.getId());
                dbUtils.saveOrUpdate(tieba);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<Tieba> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            for(Tieba tieba : list) {
                Tieba tiebaInDB = exist(context, tieba);
                if(tiebaInDB == null) {
                    dbUtils.saveOrUpdate(tieba);
                } else {
                    tieba.setId(tiebaInDB.getId());
                    dbUtils.saveOrUpdate(tieba);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Tieba exist(Context context, Tieba tieba) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            Tieba tiebaInDB = dbUtils.findFirst(Tieba.class, WhereBuilder.b().and("the_name","=",tieba.getTheName().trim()));
            if(tiebaInDB != null) {
                return tiebaInDB;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tieba> findAllByState(Context context, int state) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            return dbUtils.findAll(Tieba.class, WhereBuilder.b().and("state", "=", state));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
