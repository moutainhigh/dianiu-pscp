package com.edianniu.pscp.mis.domain;

/**
 * 电工简历
 * ClassName: ElectricianResume
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:01
 */
public class ElectricianResume extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 电工ID(member_id)
     */
    private Long electricianId;
    /**
     * 最高学历
     * 0：其他
     * 1：初中
     * 2：高中
     * 3：大专
     * 4：本科
     */
    private Integer diploma;
    /**
     * 工作年限
     */
    private Integer workingYears;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 期望费用(元/天)
     */
    private Double expectedFee;
    /**
     * 个人简介
     */
    private String synopsis;
    /**
     * 扩展字段
     */
    private String ext;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public Integer getDiploma() {
        return diploma;
    }

    public void setDiploma(Integer diploma) {
        this.diploma = diploma;
    }

    public Integer getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getExpectedFee() {
        return expectedFee;
    }

    public void setExpectedFee(Double expectedFee) {
        this.expectedFee = expectedFee;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
