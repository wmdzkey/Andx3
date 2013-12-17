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
            if(exist(context, tieba) == null) {
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
                if(exist(context, tieba) == null) {
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

}
