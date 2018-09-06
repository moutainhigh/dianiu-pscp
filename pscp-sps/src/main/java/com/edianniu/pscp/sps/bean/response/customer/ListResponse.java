package com.edianniu.pscp.sps.bean.response.customer;

import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-07-12 10:19
 */
@JSONMessage(messageCode = 2002091)
public final class ListResponse extends BaseResponse {

    private List<CustomerVO> customerList;

    public List<CustomerVO> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerVO> customerList) {
        this.customerList = customerList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
