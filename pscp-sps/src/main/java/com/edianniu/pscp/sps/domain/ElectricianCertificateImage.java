package com.edianniu.pscp.sps.domain;

import java.io.Serializable;


/**
 * 电工证书图片
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public class ElectricianCertificateImage extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long electricianId;//电工Id

    private String fileId;//图片ID

    private Integer orderNum;//排序

    private Integer status;//状态0，1，-1

    /**
     * 设置：id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：electricianId
     */
    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    /**
     * 获取：electricianId
     */
    public Long getElectricianId() {
        return electricianId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 设置：orderNum
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：orderNum
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置：status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：status
     */
    public Integer getStatus() {
        return status;
    }
}
