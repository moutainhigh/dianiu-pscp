package com.edianniu.pscp.cs.domain;

/**
 * @author zhoujianjian
 * 2017年9月13日下午9:27:26
 */
public class CommonAttachment extends BaseDo {
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //业务外键key
    private Long foreignKey;
    //公司Id
    private Long companyId;
    //会员Id
    private Long memberId;
    /**
     * 业务类型
     * 1  需求相关业务
     * 2
     */
    private Integer businessType;
    //类型     1 图片，2 视频，3其他附件
    private Integer type;
    //文件id
    private String fId;
    //排序号
    private Integer orderNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Long foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

}
