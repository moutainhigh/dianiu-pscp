package com.edianniu.pscp.sps.bean.news.VO;

import java.io.Serializable;

/**
 * ClassName: NewsInfoVO
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:54
 */
public class NewsListInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 主键ID.*/
    private Long id;
    /* 标题.*/
    private String title;
    /* 描述.*/
    private String description;
    /* 封面图.*/
    private String coverPic;
    /* 排序号.*/
    private Integer orderNum;
    /* 发布日期(yyyy.MM.dd).*/
    private String publishTime;

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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
