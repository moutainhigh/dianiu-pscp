package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;

public class SimpleMemberInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long uid;

    private String userName;

    private String mobile;

    private String txImg;

    private Integer status;
    private String nickName;
    private Integer electricianAuthStatus=ElectricianAuthStatus.NOTAUTH.getValue();
    
    private Integer companyAuthStatus=CompanyAuthStatus.NOTAUTH.getValue();
    
    private Long companyId=0L;
    
    private String companyName;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

   

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTxImg() {
        return txImg;
    }

    public void setTxImg(String txImg) {
        this.txImg = txImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getElectricianAuthStatus() {
		return electricianAuthStatus;
	}

	public void setElectricianAuthStatus(Integer electricianAuthStatus) {
		this.electricianAuthStatus = electricianAuthStatus;
	}

	public Integer getCompanyAuthStatus() {
		return companyAuthStatus;
	}

	public void setCompanyAuthStatus(Integer companyAuthStatus) {
		this.companyAuthStatus = companyAuthStatus;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

   

}
