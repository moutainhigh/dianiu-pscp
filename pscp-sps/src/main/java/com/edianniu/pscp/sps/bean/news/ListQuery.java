package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.commons.BaseQuery;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-08-10 17:39
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Integer type;
    private Integer status;
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
