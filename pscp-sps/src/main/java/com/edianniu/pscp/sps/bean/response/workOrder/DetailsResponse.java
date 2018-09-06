package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.FacilitatorWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-06-27 14:32
 */
@JSONMessage(messageCode = 2002095)
public final class DetailsResponse extends BaseResponse {
    private FacilitatorWorkOrderVO workOrder;

    private CompanyVO customerInfo;

    private CompanyVO companyInfo;

    private List<SocialWorkOrderVO> socialWorkOrderList;

    /* 修复缺陷记录.*/
    private List<DefectRecordVO> defectRepairList;
    /* 工单评价信息.*/
    private WorkOrderEvaluateInfo evaluateInfo;

    public FacilitatorWorkOrderVO getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(FacilitatorWorkOrderVO workOrder) {
        this.workOrder = workOrder;
    }

    public CompanyVO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CompanyVO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public CompanyVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public List<SocialWorkOrderVO> getSocialWorkOrderList() {
        return socialWorkOrderList;
    }

    public void setSocialWorkOrderList(List<SocialWorkOrderVO> socialWorkOrderList) {
        this.socialWorkOrderList = socialWorkOrderList;
    }

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }

    public WorkOrderEvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(WorkOrderEvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
