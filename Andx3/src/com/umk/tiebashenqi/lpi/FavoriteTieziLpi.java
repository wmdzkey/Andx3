package com.umk.tiebashenqi.lpi;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.base.BaseLpi;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
import com.umk.tiebashenqi.entity.Tiezi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public class FavoriteTieziLpi extends BaseLpi<FavoriteTiezi> {

    public void saveOrUpdate(Context context, FavoriteTiezi favoriteTiezi) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            if(exist(context, favoriteTiezi) == null) {
                dbUtils.saveOrUpdate(favoriteTiezi);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<FavoriteTiezi> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            for(FavoriteTiezi favoriteTiezi : list) {
                if(exist(context, favoriteTiezi) == null) {
                    dbUtils.saveOrUpdate(favoriteTiezi);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public FavoriteTiezi exist(Context context, FavoriteTiezi favoriteTiezi) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            FavoriteTiezi favoriteTieziInDB = dbUtils.findFirst(FavoriteTiezi.class, WhereBuilder.b().and("tieba_id","=",favoriteTiezi.getTiebaId()).and("tiezi_url","=",favoriteTiezi.getTieziUrl()));
            if(favoriteTieziInDB != null) {
                return favoriteTieziInDB;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tiezi> findTiezi(Context context, Long tiebaId) {
        List<Tiezi> tieziList = new ArrayList<Tiezi>();

        DbUtils dbUtils = getDbUtils(context);
        try {
            List<FavoriteTiezi> favoriteTieziList = dbUtils.findAll(FavoriteTiezi.class, WhereBuilder.b().and("tieba_id","=",tiebaId));
            if(favoriteTieziList != null && favoriteTieziList.size() != 0) {
                List<String> tieziUrlList = new ArrayList<String>();
                for (FavoriteTiezi favoriteTiezi : favoriteTieziList) {
                    tieziUrlList.add(favoriteTiezi.getTieziUrl());
                }
                if (tieziUrlList != null && tieziUrlList.size() != 0) {
                    tieziList = dbUtils.findAll(Selector.from(Tiezi.class).where("url", "in", tieziUrlList));
                }
                return tieziList;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return  tieziList;
    }

    public void remove(Context mContext, Tiezi group) {
        DbUtils dbUtils = getDbUtils(mContext);
        try {
            FavoriteTiezi favoriteTieziInDB = dbUtils.findFirst(FavoriteTiezi.class, WhereBuilder.b().
                    and("tieba_id","=",group.getTiebaId()).
                    and("tiezi_url","=",group.getUrl()));
            if (favoriteTieziInDB != null) {
                dbUtils.delete(favoriteTieziInDB);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
