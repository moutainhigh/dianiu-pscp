package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

/**
 * @author AbnerElk
 */
public class WalletDetailInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long uid;
    private String type;
    private Integer dealType;
    private String  dealTypeName;
    private String dealAccount;
    private String orderId;
    private Long fundTarget;
    private Long fundSource;
    private String transactionId;
    private String amount;
    private Integer checkStatus;
    private String checkTime;
    private Integer payStatus;
    private String payTime;
    private String dealTime;

    private String account;

    private String bankName;

    private String bankBranchName;

    private Long bankId;

    private String remark;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getDealAccount() {
        return dealAccount;
    }

    public void setDealAccount(String dealAccount) {
        this.dealAccount = dealAccount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Long getFundTarget() {
        return fundTarget;
    }

    public void setFundTarget(Long fundTarget) {
        this.fundTarget = fundTarget;
    }

    public Long getFundSource() {
        return fundSource;
    }

    public void setFundSource(Long fundSource) {
        this.fundSource = fundSource;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

	public Integer getDealType() {
		return dealType;
	}

	public String getDealTypeName() {
		return dealTypeName;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}
}
