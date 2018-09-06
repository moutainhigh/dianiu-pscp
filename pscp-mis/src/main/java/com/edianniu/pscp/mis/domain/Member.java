package com.edianniu.pscp.mis.domain;

/**
 * ClassName: Member
 * Author: tandingbo
 * CreateTime: 2017-04-16 13:25
 */
public class Member extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long uid;
    /**
     * 编号
     */
    private String identifier;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 头像
     */
    private String txImg;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别(默认0:保密,1:男性,2:女性)
     */
    private Integer sex = 0;
    /**
     * 年龄(默认0:保密)
     */
    private Integer age = 0;
    /**
     * 备注
     */
    private String remark;
    /**
     * 会员状态(0禁用,1正常)
     */
    private Integer status = 1;
    /**
     * 是否客户(0:否，1:是)
     */
    private Integer isCustomer = 0;
    /**
     * 是否服务商(0:否，1:是)
     */
    private Integer isFacilitator = 0;
    /**
     * 是否电工(0:否，1:是)
     */
    private Integer isElectrician = 0;
    /**
     * 是否管理员(0:否，1:是)
     */
    private Integer isAdmin = 0;

    private Long companyId;
    /**
     * 真实姓名
     */
    private String realName;
    
    private String switchpwd;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTxImg() {
        return txImg;
    }

    public void setTxImg(String txImg) {
        this.txImg = txImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Integer isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Integer getIsFacilitator() {
        return isFacilitator;
    }

    public void setIsFacilitator(Integer isFacilitator) {
        this.isFacilitator = isFacilitator;
    }

    public Integer getIsElectrician() {
        return isElectrician;
    }

    public void setIsElectrician(Integer isElectrician) {
        this.isElectrician = isElectrician;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

	public String getSwitchpwd() {
		return switchpwd;
	}

	public void setSwitchpwd(String switchpwd) {
		this.switchpwd = switchpwd;
	}
}
