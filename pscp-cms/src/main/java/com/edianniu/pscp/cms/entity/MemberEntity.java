package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统用户
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午9:28:13
 */
public class MemberEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TAG_YES=1;
	public static final int TAG_NO=0;
	public static final int SEX_NONE=0;
	public static final int SEX_MAIL=1;
	public static final int SEX_FEMAIL=2;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户编号
	 */
	private String identifier;
	/**
	 * 用户头像
	 */
	private String txImg;
	/**
	 * 用户性别
	 */
	private int sex;//默认0:保密,1:男性,2:女性
	/**
	 * 用户年龄
	 */
	private int age;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户名字
	 */
	private String realName;
	/**
	 * 是否客户
	 */
	private int isCustomer;//0:否，1:是
	/**
	 * 是否服务商
	 */
	private int isFacilitator;//0:否，1:是
	/**
	 * 是否电工
	 */
	private int isElectrician;//0:否，1:是
	/**
	 * 是否管理员
	 */
	private int isAdmin;//0:否，1:是

	/**
	 * 用户名
	 */
	private String loginName;

	/**
	 * 密码
	 */
	private transient String passwd;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	
	private Long companyId=0L;
	
	private String userName;
	private String roleName;
	
	private String memberName;
	private String companyName;
	
	/**
	 * 是否电工
	 * @return
	 */
	public boolean isElectrician(){
		if(this.getIsElectrician()==1){
			return true;
		}
		return false;
	}
	/**
	 * 是否管理员
	 * @return
	 */
	public boolean isAdmin(){
		if(this.getIsAdmin()==1){
			return true;
		}
		return false;
	}
	/**
	 * 是否服务商
	 * @return
	 */
	public boolean isFacilitator(){
		if(this.getIsFacilitator()==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 角色ID列表
	 */
	private List<Long> roleIdList;
    //TODO 
    

	/**
	 * 设置：
	 * @param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：登录名
	 * @param loginName 登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取：登录名
	 * @return String
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * 设置：密码
	 * @param password 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 获取：密码
	 * @return String
	 */
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * 设置：邮箱
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置：手机号
	 * @param mobile 手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置：状态  0：禁用   1：正常
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}
	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTxImg() {
		return txImg;
	}

	public int getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public String getRemark() {
		return remark;
	}

	public String getNickName() {
		return nickName;
	}

	public int getIsCustomer() {
		return isCustomer;
	}

	public int getIsFacilitator() {
		return isFacilitator;
	}

	public int getIsElectrician() {
		return isElectrician;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setIsCustomer(int isCustomer) {
		this.isCustomer = isCustomer;
	}

	public void setIsFacilitator(int isFacilitator) {
		this.isFacilitator = isFacilitator;
	}

	public void setIsElectrician(int isElectrician) {
		this.isElectrician = isElectrician;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMemberName() {
		if(StringUtils.isBlank(getUserName())){
			if(StringUtils.isBlank(getRealName())){
				memberName=StringUtils.isNoneBlank(getCompanyName())?getCompanyName():"";
			}
			else{
				memberName=getRealName()+(StringUtils.isNoneBlank(getCompanyName())?"("+getCompanyName()+")":"");
			}
			
		}
		else{
			if(this.isElectrician()){
				memberName=getUserName()+
						(StringUtils.isNoneBlank(getCompanyName())?"("+getCompanyName()+")":"");
			}
			else{
				memberName=getRealName()+
						(StringUtils.isNoneBlank(getCompanyName())?"("+getCompanyName()+")":"");
			}
			
		}
		return memberName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	
	
}
