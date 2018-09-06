package com.edianniu.pscp.sps.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.EvaluateInfo;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.electrician.OrderDetailInfo;
import com.edianniu.pscp.sps.bean.workorder.electrician.SettlementInfo;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-07-11 15:12
 */
@JSONMessage(messageCode = 2002111)
public final class DetailsResponse extends BaseResponse {
    private OrderDetailInfo orderDetail;
    private CompanyVO companyInfo;
    private CompanyVO customerInfo;
    private EvaluateInfo evaluateInfo;
    private SettlementInfo settlementInfo;
    private List<ElectricianWorkLogInfo> workLogList;
    /* 修复缺陷记录.*/
    private List<DefectRecordVO> defectRepairList;

    public OrderDetailInfo getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailInfo orderDetail) {
        this.orderDetail = orderDetail;
    }

    public CompanyVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public CompanyVO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CompanyVO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public EvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public SettlementInfo getSettlementInfo() {
        return settlementInfo;
    }

    public void setSettlementInfo(SettlementInfo settlementInfo) {
        this.settlementInfo = settlementInfo;
    }

    public List<ElectricianWorkLogInfo> getWorkLogList() {
        return workLogList;
    }

    public void setWorkLogList(List<ElectricianWorkLogInfo> workLogList) {
        this.workLogList = workLogList;
    }

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
