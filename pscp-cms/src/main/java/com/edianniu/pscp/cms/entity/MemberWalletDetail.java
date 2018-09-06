package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.wallet.WalleDetailPayStatus;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailCheckStatus;

public class MemberWalletDetail extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long uid;

	private Integer type;
	private String typeName;

	private Integer dealType;

	private String orderId;

	private Integer fundTarget;

	private Integer fundSource;

	private String transactionId;

	private Double amount;

	private Double availableAmount;

	private Double availableFreezingAmount;

	private String dealAccount;

	private String remark;

	private String payOrderId;

	private Integer checkStatus;

	private String checkUser;

	private String checkMemo;

	private Date checkTime;

	private Integer payStatus;

	private String payUser;

	private String payMemo;

	private Date payTime;
	
	private String payTransactionId;

	private String bankName;

	private Integer bankId;

	private String bankBranchName;

	private String bankAccount;

	private Integer status;
	private String statusName;

	/** 会员信息 **/
	private String mobile;
	private String loginName;
	private Integer isAdmin;
	private Integer isCustomer;
	private Integer isElectrician;
	private Integer isFacilitator;
	private Long companyId;
	private String userName;
	private String companyName;
	private String memberName;
	private String roleName;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getFundTarget() {
		return fundTarget;
	}

	public void setFundTarget(Integer fundTarget) {
		this.fundTarget = fundTarget;
	}

	public Integer getFundSource() {
		return fundSource;
	}

	public void setFundSource(Integer fundSource) {
		this.fundSource = fundSource;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDealAccount() {
		return dealAccount;
	}

	public void setDealAccount(String dealAccount) {
		this.dealAccount = dealAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getCheckMemo() {
		return checkMemo;
	}

	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Double getAvailableFreezingAmount() {
		return availableFreezingAmount;
	}

	public void setAvailableFreezingAmount(Double availableFreezingAmount) {
		this.availableFreezingAmount = availableFreezingAmount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getStatus() {
		if(WalletType.WITHDRAW_CASH.getValue()==type){
			if(this.checkStatus==WalletDetailCheckStatus.NORMAL.getValue()){
				this.status=0;
			}
			else if(this.checkStatus==WalletDetailCheckStatus.SUCCESS.getValue()){
				if(this.payStatus==WalleDetailPayStatus.NORMAL.getValue()){
				 this.status=1;
				}
				else if(this.payStatus==WalleDetailPayStatus.SUCCESS.getValue()){
					this.status=2;
				}
			}
			else if(this.checkStatus==WalletDetailCheckStatus.FAIL.getValue()){
				this.status=-1;
			}
			else{
				this.status=0;
			}
		}
		else{
			this.status=2;
		}
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		if(this.getStatus()==0){
			this.statusName="审核中";
		}
		else if(this.getStatus()==1){
			this.statusName="审核通过";
		}
		else if(this.getStatus()==2){
			this.statusName="已打款";
		}
		else if(this.getStatus()==-1){
			this.statusName="审核失败";
		}
		else{
			this.statusName="审核中";
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(int isCustomer) {
		this.isCustomer = isCustomer;
	}

	public int getIsFacilitator() {
		return isFacilitator;
	}

	public void setIsFacilitator(int isFacilitator) {
		this.isFacilitator = isFacilitator;
	}

	public int getIsElectrician() {
		return isElectrician;
	}

	public void setIsElectrician(int isElectrician) {
		this.isElectrician = isElectrician;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getMemberName() {
		if (StringUtils.isBlank(getUserName())) {
			memberName = StringUtils.isNoneBlank(getCompanyName()) ? getCompanyName()
					: "";
		} else {
			memberName = getUserName()
					+ (StringUtils.isNoneBlank(getCompanyName()) ? "("
							+ getCompanyName() + ")" : "");
		}
		return memberName;
	}

	public String getRoleName() {
		StringBuffer sb = new StringBuffer(64);
		if (getIsElectrician() == 1) {
			if (getCompanyId() == 0) {
				sb.append("社会电工").append("/");
			} else {
				sb.append("企业电工").append("/");
			}

		}
		if (getIsFacilitator() == 1) {
			if (getIsAdmin() == 1) {
				sb.append("服务商[主]").append("/");
			} else {
				sb.append("服务商[子]").append("/");
			}

		}
		if (getIsCustomer() == 1) {
			if (getIsAdmin() == 1) {
				sb.append("客户[主]").append("/");
			} else {
				sb.append("客户[子]").append("/");
			}
		}
		if (sb.length() == 0) {
			roleName = "普通用户";
		} else {
			roleName = sb.substring(0, sb.length() - 1);
		}
		return roleName;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
	}

	public void setIsElectrician(Integer isElectrician) {
		this.isElectrician = isElectrician;
	}

	public void setIsFacilitator(Integer isFacilitator) {
		this.isFacilitator = isFacilitator;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTypeName() {
		this.typeName=WalletType.parse(this.getType()).getDesc();
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPayTransactionId() {
		return payTransactionId;
	}

	public void setPayTransactionId(String payTransactionId) {
		this.payTransactionId = payTransactionId;
	}

	
}
