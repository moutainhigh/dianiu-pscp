package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;

import java.util.List;

/**
 * ClassName: CustomerResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 09:43
 */
public class CustomerResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<CustomerVO> customerList;

    public List<CustomerVO> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerVO> customerList) {
        this.customerList = customerList;
    }
}
