package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 会员消息信息
 * ClassName: MemberMessage
 * Author: tandingbo
 * CreateTime: 2017-04-17 15:58
 */
public class MemberMessage extends BaseDo {
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
     * 是否已读(0:未读;1已读)
     */
    private Integer isRead;
    /**
     * 是否删除(0:正常,1已删除)
     */
    private Integer isDeleted;
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}
