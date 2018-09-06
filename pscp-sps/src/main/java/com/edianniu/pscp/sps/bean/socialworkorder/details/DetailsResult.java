package com.edianniu.pscp.sps.bean.socialworkorder.details;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.details.vo.DetailsVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DetailsVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-21 17:33
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 工单信息
     */
    private WorkOrderVO workOrder;
    /**
     * 工单负责人信息
     */
    private ElectricianVO workOrderLeader;
    /**
     * 客户信息
     */
    private CompanyVO customerInfo;
    /**
     * 服务商信息
     */
    private CompanyVO companyInfo;
    /**
     * 社会电工信息
     */
    private DetailsVO details;
    /**
     * 项目信息
     */
    private ProjectVO project;
    /**
     * 社会电工已接单信息
     */
    private List<Map<String, Object>> socialElectricianList;

    public WorkOrderVO getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderVO workOrder) {
        this.workOrder = workOrder;
    }

    public ElectricianVO getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(ElectricianVO workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
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

    public DetailsVO getDetails() {
        return details;
    }

    public void setDetails(DetailsVO details) {
        this.details = details;
    }

    public ProjectVO getProject() {
        return project;
    }

    public void setProject(ProjectVO project) {
        this.project = project;
    }

    public List<Map<String, Object>> getSocialElectricianList() {
        return socialElectricianList;
    }

    public void setSocialElectricianList(List<Map<String, Object>> socialElectricianList) {
        this.socialElectricianList = socialElectricianList;
    }
}
