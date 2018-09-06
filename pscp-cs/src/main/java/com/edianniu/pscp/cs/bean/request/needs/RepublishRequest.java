package com.edianniu.pscp.cs.bean.request.needs;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * 需求重新发布
 *
 * @author zhoujianjian
 * 2017年9月14日下午4:31:20
 */
@JSONMessage(messageCode = 1002123)
public class RepublishRequest extends TerminalRequest {

    //需求编号
    private String orderId;
    //需求名称
    private String name;
    //需求描述
    private String describe;
    //材料附件
    private List<Attachment> attachmentList;
    //发布截止时间（yyyy-mm-dd）
    private String publishCutoffTime;
    //工作开始时间（yyyy-mm-dd）
    private String workingStartTime;
    //工作结束时间（yyyy-mm-dd）
    private String workingEndTime;
    //联系人
    private String contactPerson;
    //手机号码
    private String contactNumber;
    //联系地址
    private String contactAddr;
    //关联配电房ID(多个用逗号隔开)
    private String distributionRoomIds;
    //需要删除的附件ID
    private String removedImgs;

    public String getRemovedImgs() {
        return removedImgs;
    }

    public void setRemovedImgs(String removedImgs) {
        this.removedImgs = removedImgs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(String publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
    }

    public String getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(String workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getDistributionRoomIds() {
        return distributionRoomIds;
    }

    public void setDistributionRoomIds(String distributionRoomIds) {
        this.distributionRoomIds = distributionRoomIds;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
