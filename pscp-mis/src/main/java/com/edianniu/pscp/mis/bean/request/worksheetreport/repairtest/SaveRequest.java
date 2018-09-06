package com.edianniu.pscp.mis.bean.request.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveRequest
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:58
 */
@JSONMessage(messageCode = 1002162)
public final class SaveRequest extends TerminalRequest {

    /* 配电房ID.*/
    private Long roomId;
    /* 工单编号.*/
    private String orderId;
    /* 设备名称.*/
    private String deviceName;
    /* 工作内容.*/
    private String workContent;
    /* 工作日期.*/
    private String workDate;
    /* 工作负责人.*/
    private String leadingOfficial;
    /* 单位名称.*/
    private String companyName;
    /* 联系电话.*/
    private String contactNumber;
    /* 验收人.*/
    private String receiver;
    /* 备注.*/
    private String remark;
    /* 附件 */
    private List<CommonAttachment> attachmentList;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getLeadingOfficial() {
        return leadingOfficial;
    }

    public void setLeadingOfficial(String leadingOfficial) {
        this.leadingOfficial = leadingOfficial;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<CommonAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<CommonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
