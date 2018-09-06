package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 消息发送日志
 * ClassName: MemerMessageSendLog
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:02
 */
public class MemerMessageSendLog extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户ID(0:表示是所有用户)
     */
    private Long uid;
    /**
     * 类型(1:消息，2:活动)
     */
    private Integer type;
    /**
     * 类别
     */
    private String category;
    /**
     * 扩展信息
     */
    private String ext;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 返回结果
     */
    private String result;
    /**
     * 内容ID
     */
    private String contentId;
    /**
     * 任务Id
     */
    private String taskId;
    /**
     * 状态
     */
    private String status;
    /**
     * response
     */
    private String response;
    /**
     * 推送时间
     */
    private Date pushTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}
