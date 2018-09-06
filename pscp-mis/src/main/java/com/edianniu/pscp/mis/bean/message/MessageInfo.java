package com.edianniu.pscp.mis.bean.message;

import java.io.Serializable;

/**
 * ClassName: MessageInfo
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:14
 */
public class MessageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private Long id;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 类别
     */
    private String category;
    /**
     * 扩展数据
     */
    private String ext;
    /**
     * 是否已读(0:未读;1已读)
     */
    private Integer isRead;
    /**
     * 推送时间
     */
    private String pushTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }
}
