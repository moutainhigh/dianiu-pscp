package com.edianniu.pscp.sps.bean.response.needsorder;

import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: InitDataResponse
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:07
 */
@JSONMessage(messageCode = 2002173)
public final class InitDataResponse extends BaseResponse {

    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 勘查订单类型ID
     */
    private Integer type;
    /**
     * 勘查订单类型名称
     */
    private String typeName;
    /**
     * 项目信息(这里只用id,name属性)
     */
    private CompanyVO projectInfo;
    /**
     * 服务商信息
     */
    private CompanyVO companyInfo;
    /**
     * 客户信息
     */
    private CompanyVO customerInfo;
    /**
     * 危险有害因数辨识
     */
    private List<IdentificationsVO> identificationList;
    /**
     * 安全措施
     */
    private List<SafetyMeasuresVO> safetyMeasuresList;
    /**
     * 携带机械或设备
     */
    private List<ToolEquipmentVO> toolEquipmentList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public CompanyVO getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(CompanyVO projectInfo) {
        this.projectInfo = projectInfo;
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

    public List<IdentificationsVO> getIdentificationList() {
        return identificationList;
    }

    public void setIdentificationList(List<IdentificationsVO> identificationList) {
        this.identificationList = identificationList;
    }

    public List<SafetyMeasuresVO> getSafetyMeasuresList() {
        return safetyMeasuresList;
    }

    public void setSafetyMeasuresList(List<SafetyMeasuresVO> safetyMeasuresList) {
        this.safetyMeasuresList = safetyMeasuresList;
    }

    public List<ToolEquipmentVO> getToolEquipmentList() {
        return toolEquipmentList;
    }

    public void setToolEquipmentList(List<ToolEquipmentVO> toolEquipmentList) {
        this.toolEquipmentList = toolEquipmentList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
