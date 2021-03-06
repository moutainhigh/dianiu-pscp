package com.edianniu.pscp.cs.bean.response.workorder;

import com.edianniu.pscp.cs.bean.workorder.vo.CompanyVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.workorder.vo.EvaluateVO;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderDetailsVO;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-08-08 11:43
 */
@JSONMessage(messageCode = 2002116)
public final class DetailsResponse extends BaseResponse {

    /* 工单信息.*/
    private WorkOrderDetailsVO workOrder;
    /* 服务商信息.*/
    private CompanyVO companyInfo;
    /* 客户信息.*/
    private CompanyVO customerInfo;
    /* 评价信息.*/
    private EvaluateVO evaluateInfo;
    /* 修复缺陷记录.*/
    private List<DefectRecordVO> defectRepairList;

    public WorkOrderDetailsVO getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderDetailsVO workOrder) {
        this.workOrder = workOrder;
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

    public EvaluateVO getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateVO evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
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
