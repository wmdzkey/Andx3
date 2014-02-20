package com.palm.epalm.base.support.jquery.datatable.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.support.jquery.datatable.DataConverter;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * @author desire
 * @since 2013-05-01 11:42
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//转换为JSON的时候忽略空字段
public class TableResult<T> {

    private Long iTotalRecords;//实际行数
    private int iTotalDisplayRecords;//过滤之后的实际行数
    private String sEcho;//来自客户端sEcho的没有变化的复制品
    private String sColumns;//可选，以逗号分隔列名
    private String[][] aaData;//表格中的实际数据

    public Long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }


    public String[][] getAaData() {
        return aaData;
    }

    public void setAaData(String[][] aaData) {
        this.aaData = aaData;
    }

    /**
     * @param page
     * @param s
     * @return
     * @see com.palm.epalm.base.support.jquery.datatable.DataConverter
     */
    //@Deprecated
    public static <T> TableResult<T> fromPage(Page<T> page, String s) {
        DataConverter dataConverter = ApplicationHelper.getApplicationContext().getBean(DataConverter.class);
        return dataConverter.toTableResult(page);
    }

    public TableResult() {
    }

    public TableResult(Page<T> page) {
        DataConverter dataConverter = ApplicationHelper.getApplicationContext().getBean(DataConverter.class);
        HttpServletRequest request = ApplicationHelper.getCurrentHttpRequest();
        String[] fields = request.getParameter("sColumns").split(",");
        setiTotalRecords(page.getTotalElements());
        setiTotalDisplayRecords((int) page.getTotalElements());
        setAaData(dataConverter.ListToStringArray(page.getContent(), fields));
    }
}