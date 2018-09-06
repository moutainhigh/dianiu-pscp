package com.edianniu.pscp.sps.bean.news.VO;

import java.io.Serializable;

/**
 * ClassName: NewsDetailsInfoVO
 * Author: tandingbo
 * CreateTime: 2017-08-10 17:04
 */
public class NewsDetailsInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 标题.*/
    private String title;
    /* 内容.*/
    private String content;
    /* 描述.*/
    private String description;
    /* 封面图.*/
    private String coverPic;
    /* 责任编辑.*/
    private String executiveEditor;
    /* 发布日期(yyyy.MM.dd).*/
    private String publishTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getExecutiveEditor() {
        return executiveEditor;
    }

    public void setExecutiveEditor(String executiveEditor) {
        this.executiveEditor = executiveEditor;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
