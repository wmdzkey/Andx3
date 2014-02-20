package com.palm.epalm.modules.system.util;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 数据存储中Ids拼接技术，拼接工具类<br/>
 *
 * 加入 <br/>
 * 是否存在 <br/>
 * 删除 <br/>
 * 排序 <br/>
 * 数量 <br/>
 *
 * 检查(格式) <br/>
 * 格式化 <br/>
 * 去重 <br/>
 *
 * 将 ;1;2; 转成 (1,2) 形式 idStrToIds <br/>
 * 转换ids到List idStrToLongArr <br/>
 * 转换ids到List idStrToIntArr <br/>
 * 转换List到ids, ids 形如：1,2,3  listToIds <br/>
 *
 * @author Winnid
 * Created by Winnid on 13-8-30.
 */
public class IdsUtil {


    /***
     * 存在 返回 True/False
     */
    public static boolean exsit(String ids, String id) {
        //空排除
        if(!idNullCheck(id)) throw new NullPointerException("Parameter id is Null");
        ids = idsNullCheck(ids);

        //格式化
        ids = format(ids);
        //去重
        ids = deleteRepeat(ids);

        //解析字符串
        StringTokenizer tokenizer = new StringTokenizer(ids, ";");
        while (tokenizer.hasMoreTokens()) {
            try {
                if(tokenizer.nextToken().trim().equals(id)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***
     * 加入 返回 ids
     */
    public static String add(String ids, String id) {
        //空排除
        if(!idNullCheck(id)) throw new NullPointerException("Parameter id is Null");
        ids = idsNullCheck(ids);
        //是否存在,不存在 = false //继续执行
        if(exsit(ids, id)) {
            return null;
        }

        //加入
        ids += id + ";";
        //排序
        ids = sort(ids);

        return ids;
    }

    /***
     * 数量 返回 int
     */
    public static int length(String ids) {
        //空检查
        ids = idsNullCheck(ids);
        //格式化
        ids = format(ids);
        //去重
        ids = deleteRepeat(ids);

        //数量计算
        StringTokenizer tokenizer = new StringTokenizer(ids, ";");
        return  tokenizer.countTokens();
    }
    /***
     * 删除 返回 ids
     */
    public static String remove(String ids, String id) {
        //空排除
        if(!idNullCheck(id)) throw new NullPointerException("Parameter id is Null");
        ids = idsNullCheck(ids);
        //是否存在,存在 = !true //继续执行
        if(!exsit(ids, id)) {
            return null;
        }
        //删除
        if(ids.contains(";"+id+";"));
            ids = ids.replace(";"+id+";", ";");
        return ids;
    }
    /***
     * 排序 返回 String
     */
    public static String sort(String ids) {
        //空检查
        ids = idsNullCheck(ids);
        //拆解
        List<String> idsList = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(ids, ";");
        while (tokenizer.hasMoreTokens()) {
            try {
                String s = tokenizer.nextToken();
                idsList.add(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] idsArray = idsList.toArray(new String[idsList.size()]);
        Arrays.sort(idsArray);

        String newIds = new String(";");
        for(String id : idsArray) {
            newIds += id + ";";
        }
        return newIds;
    }
    /***
     * 检查(格式)
     */
    public static boolean check(String ids) {
        //空排除
        ids = idsNullCheck(ids);

        Pattern pattern = Pattern.compile("[0-9]*");
        StringTokenizer st = new StringTokenizer(ids, ";");
        while( st.hasMoreElements() ){
            String s = st.nextToken();
            //是数字
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }
    /***
     * 格式化
     */
    public static String format(String ids) {
        //空检查
        ids = idsNullCheck(ids);

        //格式化
        Pattern pattern = Pattern.compile("[0-9]*");
        StringTokenizer st = new StringTokenizer(ids, ";");
        //三种情况
        //1.没有元素
        if(st.countTokens() == 0) {
            ids = new String(";");
        }
        //2.前面元素没有;
        if(st.countTokens() != 0 && !ids.startsWith(";")) {
            ids = ";" + ids;
        }
        //3.最后元素没有;
        if(st.countTokens() != 0 && !ids.endsWith(";")) {
            ids = ids + ";";
        }
        while( st.hasMoreElements() ){
            String s = st.nextToken();
            //是数字
            if (!pattern.matcher(s).matches()) {
                return null;
            }
        }
        return ids;
    }

    /***
     * 去重
     */
    public static String deleteRepeat(String ids) {
        //空检查
        ids = idsNullCheck(ids);

        //去重
        StringTokenizer st = new StringTokenizer(ids, ";");
        Set<String> set = new HashSet<String>();
        while( st.hasMoreElements() ){
            String s = st.nextToken();
            set.add(s);
        }
        String newIds = ";";
        for(String i : set) {
            newIds += i + ";";
        }
        return newIds;
    }

    /***
     * 格式化并去重
     */
    public static String formatDeleteRepeat(String ids) {
        ids = format(ids);
        ids = deleteRepeat(ids);
        return ids;
    }

    //id空检查(返回null)
    public static boolean idNullCheck(String id) {
        if(id == null || id.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    //ids空检查(返回;)
    public static String idsNullCheck(String ids) {
        if(ids == null || ids.trim().equals("")) {
            return new String(";");
        } else {
            return ids;
        }
    }

    /**
     * 将;1;2; 转成 (1,2) 形式
     * 主要用于in语句
     */
    public static String idStrToIds(String idStr) {
        idStr = formatDeleteRepeat(idStr);
        idStr = idStr.replaceFirst("^[;,]*", "");
        idStr = idStr.replaceAll("[;,]*$", "");
        return "(" + idStr.replace(";", ",") + ")";
    }

    public static List<Long> idStrToLongArr(String idStr) {
        List<Long> list = new ArrayList<Long>();
        String [] ids = idStr.split("[,;]");
        for (String id : ids) {
            if (" ,;".contains(id)) {
                continue;
            }
            list.add(Long.parseLong(id));
        }
        return list;
    }

    public static List<Integer> idStrToIntArr(String idStr) {
        List<Integer> list = new ArrayList<Integer>();
        String [] ids = idStr.split("[,;]");
        for (String id : ids) {
            if (" ,;".contains(id)) {
                continue;
            }
            list.add(Integer.parseInt(id));
        }
        return list;
    }


    /**
     * list to ids, ids 形如：1,2,3
     * @param idsList
     * @return
     */
    public static <T> String listToIds(List<T> idsList) {
        return org.apache.commons.lang.StringUtils.join(idsList, ",");
    }

}
