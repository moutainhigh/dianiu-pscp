package com.edianniu.pscp.search.support.query;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-10-17 23:22
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;

    private String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
