package com.edianniu.pscp.cms.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 会员钱包
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-31 19:16:24
 */
public class MemberWallet extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long uid;
    //$column.comments
    private Double amount;
    //$column.comments
    private Double freezingAmount;
    /**会员信息**/
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
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置：${column.comments}
     */
    public void setFreezingAmount(Double freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getFreezingAmount() {
        return freezingAmount;
    }

	public String getMobile() {
		return mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public Integer getIsCustomer() {
		return isCustomer;
	}

	public Integer getIsElectrician() {
		return isElectrician;
	}

	public Integer getIsFacilitator() {
		return isFacilitator;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getUserName() {
		return userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMemberName() {
		if(StringUtils.isBlank(getUserName())){
			memberName=StringUtils.isNoneBlank(getCompanyName())?getCompanyName():"";
		}
		else{
			memberName=getUserName()+
					(StringUtils.isNoneBlank(getCompanyName())?"("+getCompanyName()+")":"");
		}
		return memberName;
	}

	public String getRoleName() {
		StringBuffer sb=new StringBuffer(64);
		if(getIsElectrician()==1){
			if(getCompanyId()==0){
				sb.append("社会电工").append("/");
			}
			else{
				sb.append("企业电工").append("/");
			}
			
		}
		if(getIsFacilitator()==1){
			if(getIsAdmin()==1){
				sb.append("服务商[主]").append("/");
			}
			else{
				sb.append("服务商[子]").append("/");
			}
			
		}
		if(getIsCustomer()==1){
			if(getIsAdmin()==1){
				sb.append("客户[主]").append("/");
			}
			else{
				sb.append("客户[子]").append("/");
			}
		}
		if(sb.length()==0){
			roleName="普通用户";
		}
		else{
			roleName=sb.substring(0, sb.length()-1);
		}
		return roleName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
