package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.FacilitatorWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;

import java.util.List;

/**
 * 服务商APP工单详情接口返回数据结构
 * ClassName: FacilitatorAppDetailsResult
 * Author: tandingbo
 * CreateTime: 2017-07-26 16:42
 */
public class FacilitatorAppDetailsResult extends Result {
    private static final long serialVersionUID = 1L;

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

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }

    public void setSocialWorkOrderList(List<SocialWorkOrderVO> socialWorkOrderList) {
        this.socialWorkOrderList = socialWorkOrderList;
    }

    public WorkOrderEvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(WorkOrderEvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }
}
