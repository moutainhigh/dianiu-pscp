package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.mis.bean.workorder.WorkOrderTypeInfo;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;

import java.util.List;

/**
 * ClassName: InitDataResult
 * Author: tandingbo
 * CreateTime: 2017-06-28 10:24
 */
public class InitDataResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 派单单位
     */
    private String name;
    /**
     * 派单单位负责人
     */
    private String contacts;
    /**
     * 派单单位联系电话
     */
    private String phone;
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
    /**
     * 工单类型列表
     */
    private List<WorkOrderTypeInfo> orderTypeList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public List<WorkOrderTypeInfo> getOrderTypeList() {
        return orderTypeList;
    }

    public void setOrderTypeList(List<WorkOrderTypeInfo> orderTypeList) {
        this.orderTypeList = orderTypeList;
    }
}
