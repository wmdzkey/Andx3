package com.palm.epalm.base.support.jquery.datatable;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.support.jquery.datatable.vo.TableResult;
import com.palm.epalm.base.util.Reflections;
import com.palm.epalm.base.util.converter.StringConverter;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -数据转换，把Java实体转换为字符串数组
 *
 * @author desire
 * @since 2013-05-03 13:18
 */
public class DataConverter {
    private static Map<String, StringConverter> converters = new HashMap<String, StringConverter>();

    // HttpServletRequest request;
    public DataConverter() {
        //     request = ApplicationHelper.getCurrentHttpRequest();
    }

    public TableResult toTableResult(Page page) {
        HttpServletRequest request = ApplicationHelper.getCurrentHttpRequest();
        return fromPage(page, request.getParameter("sColumns"));
    }

    private TableResult fromPage(Page page, String s_fields) {
        String[] fields = s_fields.split(",");
        TableResult tableResult = new TableResult();
        tableResult.setiTotalRecords(page.getTotalElements());
        tableResult.setiTotalDisplayRecords((int) page.getTotalElements());
        tableResult.setAaData(ListToStringArray(page.getContent(), fields));
        return tableResult;
    }

    public String[] ObjectToArray(Object o, String[] fields) {
        String[] result = new String[fields.length];
        for (int i = fields.length - 1; i >= 0; i--) {
            String f = fields[i];
            if (f.contains(".")) {
                String[] fs = f.split("\\.");
                Object o1 = o;
                for (String f1 : fs) {
                    if (o1 == null) break;
                    o1 = Reflections.getFieldValue(o1, f1);
                }
                result[i] = ObjectToString(o1);
            } else {
                result[i] = ObjectToString(Reflections.getFieldValue(o, fields[i]));
            }
        }
        return result;
    }

    public String[][] ListToStringArray(List list, String[] fields) {
        String[][] data = new String[list.size()][];
        for (int i = list.size() - 1; i >= 0; i--) {
            data[i] = ObjectToArray(list.get(i), fields);
        }
        return data;
    }

    public List<String[]> ListToStringList(List list, String[] fields) {
        //String [][]data=new String[list.size()][];
        List<String[]> data = new ArrayList<String[]>();
        for (int i = list.size() - 1; i >= 0; i--) {
            data.add(ObjectToArray(list.get(i), fields));
            //data[i]=ObjectToArray(list.get(i),fields);
        }
        return data;
    }

    public String ObjectToString(Object o) {
        if (null == o) return null;
        String key = o.getClass().getName();
        if (converters.containsKey(key)) {
            return converters.get(key).convert(o);
        }
        return o.toString();
    }

    public void setConverters(Map<String, StringConverter> converters) {
        this.converters = converters;
    }
}
