package com.edianniu.pscp.cs.bean.query;

import com.edianniu.pscp.cs.commons.BaseQuery;

import java.util.List;

/**
 * ClassName: NeedsMarketListQuery
 * Author: tandingbo
 * CreateTime: 2017-09-21 10:44
 */
public class NeedsMarketListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long companyId;

    private List<String> orderIdList;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<String> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<String> orderIdList) {
        this.orderIdList = orderIdList;
    }
}
