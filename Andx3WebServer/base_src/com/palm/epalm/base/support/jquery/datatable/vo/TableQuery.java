package com.palm.epalm.base.support.jquery.datatable.vo;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.base.util.StringEncodeUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author desire
 * @since 2013-05-01 11:49
 */
public class TableQuery {
    HttpServletRequest request;
    Boolean[] bSearchable;// bSearchable_(int)表示一列是否在客户端被标志位可搜索
    Boolean[] bSortable;// bSortable_(int);表示一列是否在客户端被标志位可排序
    int iDisplayStart;// 显示的起始索引
    int iDisplayLength;// 显示的行数
    int iColumns;// 显示的列数
    String sSearch;// 全局搜索字段
    Boolean bEscapeRegex;// 全局搜索是否正则
    String[] sSearches_;//sSearch_(int) 个别列的搜索
    Boolean[] bEscapeRegex_;//bEscapeRegex_(int) 个别列是否使用正则搜索
    int iSortingCols;//  排序的列数
    int[] iSortCol;//iSortCol_(int) 被排序的列
    int iSortCol_0;
    String sSortDir_0;
    String[] sSortDir;//sSortDir_(int) 排序的方向 "desc" 或者 "asc".
    String sEcho;//  DataTables 用来生成的信息
    String sColumns;
    String[] columnNames;

    public TableQuery() {
        this.request = ApplicationHelper.getCurrentHttpRequest();
        // prise(request);
    }

    public String getSearchable() {
        return searchable;
    }

    public void setSearchable(String searchable) {
        String[] ss = searchable.split(",");
        bSearchable = new Boolean[ss.length];
        for (int i = ss.length - 1; i >= 0; i--) bSearchable[i] = Boolean.valueOf(ss[i]);
        this.searchable = searchable;
    }

    String searchable;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiColumns() {
        return iColumns;
    }

    public void setiColumns(int iColumns) {
        this.iColumns = iColumns;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = StringEncodeUtil.code_8859_1_to_UTF_8(sSearch);
    }

    public Boolean isbEscapeRegex() {
        return bEscapeRegex;
    }

    public void setbEscapeRegex(Boolean bEscapeRegex) {
        this.bEscapeRegex = bEscapeRegex;
    }

    public Boolean[] getbSortable() {
        return bSortable;
    }

    public void setbSortable_(Boolean[] bSortable) {
        this.bSortable = bSortable;
    }

    public Boolean[] getbSearchable() {
        return bSearchable;
    }

    public void setbSearchable(Boolean[] bSearchable) {
        this.bSearchable = bSearchable;
    }

    public String[] getsSearches_() {
        return sSearches_;
    }

    public void setsSearches_(String[] sSearches_) {
        this.sSearches_ = sSearches_;
    }

    public Boolean[] getbEscapeRegex_() {
        return bEscapeRegex_;
    }

    public void setbEscapeRegex_(Boolean[] bEscapeRegex_) {
        this.bEscapeRegex_ = bEscapeRegex_;
    }

    public int getiSortingCols() {
        return iSortingCols;
    }

    public void setiSortingCols(int iSortingCols) {
        this.iSortingCols = iSortingCols;
    }

    public int[] getiSortCol() {
        return iSortCol;
    }

    public void setiSortCol(int[] iSortCol) {
        this.iSortCol = iSortCol;
    }

    public String[] getsSortDir() {
        return sSortDir;
    }

    public void setsSortDir(String[] sSortDir) {
        this.sSortDir = sSortDir;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        columnNames = sColumns.split(",");
        this.sColumns = sColumns;
    }

    public int getiSortCol_0() {
        return iSortCol_0;
    }

    public void setiSortCol_0(int iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    public String getsSortDir_0() {
        return sSortDir_0;
    }

    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        prise(request);
        this.request = request;
    }

    public Pageable toPageAble() {
        Sort sort = new Sort(Sort.Direction.fromString(getsSortDir_0()), columnNames[getiSortCol_0()]);
        PageRequest pageRequest = new PageRequest(iDisplayStart / iDisplayLength, iDisplayLength, sort);

        return pageRequest;
    }

    private void prise(HttpServletRequest request) {
        Enumeration<String> pNames = request.getParameterNames();
        List<Boolean> sortable = new ArrayList<Boolean>();
        //List<Boolean> searchable = new ArrayList<Boolean>();
        Map<Integer, Boolean> searchable = new TreeMap<Integer, Boolean>();
        while (pNames.hasMoreElements()) {
            String name = pNames.nextElement();
            if (name.startsWith("bShortable_")) {
                int index = Integer.valueOf(name.replace("bShortable_", ""));
                //   sortable.add(index,Boolean.valueOf(request.getParameter(name)));
            } else if (name.startsWith("bSearchable_")) {
                int index = Integer.valueOf(name.replace("bSearchable_", ""));
                searchable.put(index, Boolean.valueOf(request.getParameter(name)));
            }
        }
        //     bSortable = sortable.toArray(bSortable);
        bSearchable = searchable.values().toArray(new Boolean[]{});
    }

    public QueryFilter getPropertyFilter() {
        String filter = "SLIKE";
        for (int i = 0; i < bSearchable.length; i++) {
            if (bSearchable[i]) {
                filter += "_" + columnNames[i];
            }
        }
        if ("SLIKE".equals(filter)) {
            return null;
        }
        return new QueryFilter(filter, sSearch);
    }

    public QueryFilter[] appendToPropertyFilters(QueryFilter[] filters) {
        QueryFilter f = getPropertyFilter();
        if (f == null) return filters;
        if (filters == null) return new QueryFilter[]{f};
        int l = filters.length;
        QueryFilter[] fs = new QueryFilter[l + 1];
        System.arraycopy(filters, 0, fs, 0, l);
        fs[l] = f;
        return fs;
    }
}
