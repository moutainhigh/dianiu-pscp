package com.edianniu.pscp.mis.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.*;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: DetailResponse
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:14
 */
@JSONMessage(messageCode = 2002018)
public final class DetailResponse extends BaseResponse {
    /**
     * 订单详情
     */
    private OrderDetailInfo orderDetail;
    /**
     * 服务商信息
     */
    private CompanyInfo compayInfo;
    /**
     * 客户信息
     */
    private CustmerInfo custmerInfo;
    /**
     * 结算信息
     */
    private SettlementInfo settlementInfo;
    /**
     * 评价信息
     */
    private EvaluateInfo evaluateInfo;
    /**
     * 修复缺陷记录
     */
    private List<DefectRecordVO> defectRepairList;
    /**
     * 派单人信息
     */
    private DispatchUserInfo dispatchUserInfo;

    public OrderDetailInfo getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailInfo orderDetail) {
        this.orderDetail = orderDetail;
    }

    public CompanyInfo getCompayInfo() {
        return compayInfo;
    }

    public void setCompayInfo(CompanyInfo compayInfo) {
        this.compayInfo = compayInfo;
    }

    public CustmerInfo getCustmerInfo() {
        return custmerInfo;
    }

    public void setCustmerInfo(CustmerInfo custmerInfo) {
        this.custmerInfo = custmerInfo;
    }

    public SettlementInfo getSettlementInfo() {
        return settlementInfo;
    }

    public void setSettlementInfo(SettlementInfo settlementInfo) {
        this.settlementInfo = settlementInfo;
    }

    public EvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }

    public DispatchUserInfo getDispatchUserInfo() {
        return dispatchUserInfo;
    }

    public void setDispatchUserInfo(DispatchUserInfo dispatchUserInfo) {
        this.dispatchUserInfo = dispatchUserInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
