package com.edianniu.pscp.sps.domain;

/**
 * 新闻
 * ClassName: News
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:16
 */
public class News extends BaseDo {
    private static final long serialVersionUID = 1L;

    /* 主键ID.*/
    private Long id;
    /* 标题.*/
    private String title;
    /* 内容.*/
    private String content;
    /* 描述.*/
    private String description;
    /* 封面图.*/
    private String coverPic;
    /* 类型.*/
    private Integer type;
    /* 状态(0:未发布,1:已发布).*/
    private Integer status;
    /* 排序号.*/
    private Integer orderNum;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
