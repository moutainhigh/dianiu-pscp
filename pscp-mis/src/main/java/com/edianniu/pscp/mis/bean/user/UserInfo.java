package com.edianniu.pscp.mis.bean.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private String identifier;
    private String nickName;
    private String loginName;
    private String mobile;
    @JSONField(serialize = false)
    private String passwd;
    private String txImg;
    private Integer age;
    private Integer sex;

    private String remark;
    private Integer status;

    private Integer isCustomer;
    private Integer isFacilitator;
    private Integer isElectrician;
    private Integer isAdmin;
    private Long companyId;
    
    /** 用户真实姓名 */
    private String realName;
    
    /** 开合闸操作密码 */
    @JSONField(serialize = false)
    private String switchpwd;

    @JSONField(serialize = false)
    public boolean isNormalMember() {
        if (isCustomer == null || isFacilitator == null || isElectrician == null) {
            return false;
        }
        if (isCustomer == 0 && isFacilitator == 0 && isElectrician == 0) {
            return true;
        }
        return false;
    }

    /**
     * 电工类型
     * 企业电工:true
     * 社会电工:false
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isCompanyElectrician() {
        if (getCompanyId() == null || getIsElectrician() == null) {
            return false;
        }
        return (!getCompanyId().equals(0L) && getIsElectrician().equals(1));
    }

    /**
     * 是否企业用户
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isCompanyUser() {
        if (this.getCompanyId() == null) {
            return false;
        }
        if (this.getCompanyId().equals(0L)) {
            return false;
        }
        return true;
    }

    /**
     * 是否社会电工
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isSocialElectrician() {

        if (getCompanyId() == null || getIsElectrician() == null) {
            return false;
        }
        return (getCompanyId().equals(0L) && getIsElectrician().equals(1));
    }

    /**
     * 是否电工
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isElectrician() {

        if (getCompanyId() == null || getIsElectrician() == null) {
            return false;
        }
        return (getIsElectrician().equals(1));
    }

    /**
     * 是否服务商
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isFacilitator() {
        if (this.getIsFacilitator() != null && this.getIsFacilitator() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 是否客户
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isCustomer() {
        if (this.getIsCustomer() != null && this.getIsCustomer() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 是否服务商管理员
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isFacilitatorAdmin() {
        if (this.getIsFacilitator() != null &&
                this.getIsFacilitator() == 1 &&
                this.getIsAdmin() != null &&
                this.getIsAdmin() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 非服务商管理员
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isNotFacilitatorAdmin() {
        if (getIsFacilitator() != null &&
                getIsFacilitator() == 1 &&
                getIsAdmin() != null &&
                getIsAdmin() == 0) {
            return true;
        }
        return false;
    }

    //操作人
    @JSONField(serialize = false)
    private String opUser;
    @JSONField(serialize = false)
    private String modifiedUser = "系统";

    public Long getUid() {
        return uid;
    }


    public void setUid(Long uid) {
        this.uid = uid;
    }


    public String getIdentifier() {
        return identifier;
    }


    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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


    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getSex() {
        return sex;
    }


    public void setSex(Integer sex) {
        this.sex = sex;
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


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getOpUser() {
        return opUser;
    }


    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }


    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
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

	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }

}
