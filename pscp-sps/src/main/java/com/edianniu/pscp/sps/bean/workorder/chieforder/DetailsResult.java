package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;

/**
 * ClassName: DetailsVOResult
 * Author: tandingbo
 * CreateTime: 2017-06-28 09:59
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 订单详情
     */
    private WorkOrderVO orderDetail;

    public WorkOrderVO getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(WorkOrderVO orderDetail) {
        this.orderDetail = orderDetail;
    }
}
