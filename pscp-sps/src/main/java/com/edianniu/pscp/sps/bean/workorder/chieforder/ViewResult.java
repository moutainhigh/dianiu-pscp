package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ViewResult
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:56
 */
public class ViewResult extends Result {
    private static final long serialVersionUID = 1L;

    private WorkOrderVO workOrder;
    /* 客户信息. */
    private CompanyVO customerInfo;
    /* 服务商信息. */
    private CompanyVO companyInfo;

    private ElectricianVO workOrderLeader;
    // 电工工单信息(检修项目信息)
    private List<Map<String, Object>> companyWorkOrder;

    private List<SocialWorkOrderVO> socialWorkOrderList;

    private ProjectVO projectVO;

    // 危险有害因数辨识
    private List<Map> hazardFactorIdentifications;
    // 危险有害因数辨识-其它
    private String identificationText;
    // 安全措施
    private List<Map> safetyMeasures;
    // 安全措施-其它
    private String measureText;
    // 携带机械或设备
    private List<Map> carryingTools;
    /* 修复缺陷记录.*/
    private List<DefectRecordVO> defectRepairList;

    public WorkOrderVO getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderVO workOrder) {
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

    public ElectricianVO getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(ElectricianVO workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    public List<Map<String, Object>> getCompanyWorkOrder() {
        return companyWorkOrder;
    }

    public void setCompanyWorkOrder(List<Map<String, Object>> companyWorkOrder) {
        this.companyWorkOrder = companyWorkOrder;
    }

    public List<SocialWorkOrderVO> getSocialWorkOrderList() {
        return socialWorkOrderList;
    }

    public void setSocialWorkOrderList(List<SocialWorkOrderVO> socialWorkOrderList) {
        this.socialWorkOrderList = socialWorkOrderList;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public List<Map> getHazardFactorIdentifications() {
        return hazardFactorIdentifications;
    }

    public void setHazardFactorIdentifications(List<Map> hazardFactorIdentifications) {
        this.hazardFactorIdentifications = hazardFactorIdentifications;
    }

    public String getIdentificationText() {
        return identificationText;
    }

    public void setIdentificationText(String identificationText) {
        this.identificationText = identificationText;
    }

    public List<Map> getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(List<Map> safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getMeasureText() {
        return measureText;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public List<Map> getCarryingTools() {
        return carryingTools;
    }

    public void setCarryingTools(List<Map> carryingTools) {
        this.carryingTools = carryingTools;
    }

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }
}
