package com.palm.epalm.modules.system.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleListPageUtil {

    /**
     * 通过大小和页号加载数据
     * */
    public static List createList(List list, int pageSize, int pageNo) {

        //防止空集合
        if(list == null) {
            list = new ArrayList();
        }

        //有可能是0，或者其他
        int count = list.size();

        //总页数
        int maxPageNo = (int)Math.ceil(count*1.0/pageSize);

        //判断当前页
        if( Math.ceil(count*1.0/pageSize) < pageNo ) {
            return new ArrayList();
        } else if (pageNo < 1){
            return new ArrayList();
        }//如果上面两个条件排除则返回正确集合

        //建立索引
        int fromIndex = (pageNo-1) * pageSize;
        int toIndex = 0;
        if(count < pageNo * pageSize) {
            toIndex = count;
        } else {
            toIndex = pageNo * pageSize;
        }

        return list.subList(fromIndex, toIndex);
    }

    /**
     * 加载剩余全部
     */
    public static List createRemainList(List list, int pageSize, int pageNo) {

        //防止空集合
        if(list == null) {
            list = new ArrayList();
        }

        //有可能是0，或者其他
        int count = list.size();

        //总页数
        int maxPageNo = (int)Math.ceil(count*1.0/pageSize);

        //判断当前页
        if( Math.ceil(count*1.0/pageSize) < pageNo ) {
            return new ArrayList();
        } else if (pageNo < 1){
            return new ArrayList();
        }//如果上面两个条件排除则返回正确集合

        //建立索引
        int fromIndex = (pageNo-1) * pageSize;
        int toIndex = count;//到最后一条

        return list.subList(fromIndex, toIndex);
    }

}