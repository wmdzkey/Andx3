package com.umk.andx3.base;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.umk.andx3.util.AppInfoUtil;
import com.umk.tiebashenqi.entity.Tieba;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public abstract class BaseLpi<T> {

    protected Class<T> entityClass;
    protected DbUtils dbUtils;

    public BaseLpi(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public DbUtils getDbUtils(Context context) {
        if(dbUtils == null) {
            dbUtils = DbUtils.create(context, AppInfoUtil.getPackageName(context));//TODO:这里要做成多用户的必须改为加用户id身份标识作区分,还有是否写成静态？还有切换用户之后这里要能知道需要重新生成
            try {
                dbUtils.createTableIfNotExist(entityClass);
            } catch (DbException e) {
                dbUtils = null;
            }
        }
        return dbUtils;
    }



    public void saveOrUpdate(Context context, T t) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            dbUtils.saveOrUpdate(t);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdate(Context context, List<T> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            dbUtils.saveOrUpdate(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, T t) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            dbUtils.delete(t);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Context context, List<T> list) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            dbUtils.deleteAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public T find(Context context, Long id) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            return dbUtils.findById(entityClass, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll(Context context) {
        DbUtils dbUtils = getDbUtils(context);
        try {
            return dbUtils.findAll(entityClass);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T exist(Context context, T t, Map<String, Object> map) {
        DbUtils dbUtils = DbUtils.create(context);
        try {
            WhereBuilder whereBuilder =  WhereBuilder.b();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                whereBuilder.and(entry.getKey(), "=", entry.getValue());
            }
            T tInDB = dbUtils.findFirst(entityClass, whereBuilder);
            if(tInDB != null) {
                return tInDB;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
