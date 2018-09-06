package com.edianniu.pscp.mis.bean.request.defectrecord;

import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveRequest
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:42
 */
@JSONMessage(messageCode = 1002157)
public final class SaveRequest extends TerminalRequest {

    private Long roomId;
    private String orderId;
    private String deviceName;
    private String defectContent;
    private String discoveryDate;
    private String discoverer;
    private String discoveryCompany;
    private String contactNumber;
    private String remark;
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

    public String getDefectContent() {
        return defectContent;
    }

    public void setDefectContent(String defectContent) {
        this.defectContent = defectContent;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public String getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    public String getDiscoveryCompany() {
        return discoveryCompany;
    }

    public void setDiscoveryCompany(String discoveryCompany) {
        this.discoveryCompany = discoveryCompany;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
