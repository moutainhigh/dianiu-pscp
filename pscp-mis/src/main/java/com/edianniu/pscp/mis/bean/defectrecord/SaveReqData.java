package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;
import java.util.List;
import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;

/**
 * ClassName: SaveReqData
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:41
 */
public class SaveReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

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
    
}
