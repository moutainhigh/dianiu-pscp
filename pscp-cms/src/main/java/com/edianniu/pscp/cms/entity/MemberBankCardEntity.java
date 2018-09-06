package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.edianniu.pscp.mis.bean.wallet.BankType;



/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:12:59
 */
public class MemberBankCardEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private Long uid;
	//$column.comments
	private Long bankId;
	//$column.comments
	private String bankName;
	//$column.comments
	private String bankBranchName;
	//$column.comments
	private String account;
	//$column.comments
	private Integer status;
	//$column.comments
	private Date createTime;
	//$column.comments
	private String createUser;
	//$column.comments
	private Date modifiedTime;
	//$column.comments
	private String modifiedUser;
	//$column.comments
	private Integer deleted;
	//$column.comments
	private String remark;

	private Long provinceId;
	
	private Long cityId;
	
	//扩展属性仅用作展示
		private String companyName;
		
		private String provinceName;
		
		private String cityName;
		
		private List<BankType>banks;	
		
		public List<BankType> getBanks() {
			return banks;
		}
		public void setBanks(List<BankType> banks) {
			this.banks = banks;
		}
	/**
	 * 设置：${column.comments}
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getBankId() {
		return bankId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getModifiedUser() {
		return modifiedUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getRemark() {
		return remark;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
