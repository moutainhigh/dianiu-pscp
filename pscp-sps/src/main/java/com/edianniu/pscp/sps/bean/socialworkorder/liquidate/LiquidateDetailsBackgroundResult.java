package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: LiquidateDetailsBackgroundResult
 * Author: tandingbo
 * CreateTime: 2017-07-20 17:56
 */
public class LiquidateDetailsBackgroundResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 工单
     */
    private WorkOrderVO workOrder;
    /**
     * 客户信息
     */
    private CompanyVO customerInfo;
    /**
     * 服务商信息
     */
    private CompanyVO companyInfo;
    /**
     * 项目
     */
    private ProjectVO project;
    /**
     * 项目负责人
     */
    private ElectricianVO workOrderLeader;
    /**
     * 社会电工结算信息
     */
    private List<Map<String, Object>> electricianWorkOrderList;

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

    public ProjectVO getProject() {
        return project;
    }

    public void setProject(ProjectVO project) {
        this.project = project;
    }

    public ElectricianVO getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(ElectricianVO workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    public List<Map<String, Object>> getElectricianWorkOrderList() {
        return electricianWorkOrderList;
    }

    public void setElectricianWorkOrderList(List<Map<String, Object>> electricianWorkOrderList) {
        this.electricianWorkOrderList = electricianWorkOrderList;
    }
}
