package com.edianniu.pscp.mis.bean.request.workexperience;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveOrUpdateRequest
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:26
 */
@JSONMessage(messageCode = 1002054)
public final class SaveOrUpdateRequest extends TerminalRequest {

    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 工作经验ID(为空：新增操作，不为空：修改操作)
     */
    private Long experienceId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 工作内容
     */
    private String workContent;
    /**
     * 开始时间:yyyy-MM
     */
    private String startTime;
    /**
     * 结束时间:yyyy-MM
     */
    private String endTime;
    /**
     * 排序号
     */
    private String orderNum;

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
