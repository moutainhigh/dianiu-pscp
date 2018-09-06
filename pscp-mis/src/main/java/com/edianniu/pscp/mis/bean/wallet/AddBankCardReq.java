package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

public class AddBankCardReq implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long uid;

    private String account;

    private Long bankId;

    private String bankBranchName;

    private String msgcodeid;

    private String msgcode;
    
    private Long provinceId;
    
    private Long cityId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getMsgcodeid() {
        return msgcodeid;
    }

    public void setMsgcodeid(String msgcodeid) {
        this.msgcodeid = msgcodeid;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}


}
