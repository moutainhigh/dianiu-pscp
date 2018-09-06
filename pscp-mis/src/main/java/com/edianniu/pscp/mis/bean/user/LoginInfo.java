package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;
import java.util.Date;

public class LoginInfo implements Serializable {
	public static  final String APP_TYPE_ELECTRICIAN="electrician";
	public static  final String APP_TYPE_FACILITATOR="facilitator";
	public static  final String APP_TYPE_CUSTOMER="customer";
	public static  final String APP_TYPE_RENTER="renter";
	private static final long serialVersionUID = 1L;
	private Date loginTime;
	private String token;
	private String osType;
	private String did;
	private String pbrand;
	private String ptype;
	private String appType="";//应用类型 electrician |facilitator|customer|renter
	private String appPkg="";
	
	public String getAppType() {
		return appType;
	}
	public boolean isElectricianApp(){
		if(appType.equals(APP_TYPE_ELECTRICIAN)){
			return true;
		}
		return false;
	}
	public boolean isFacilitatorApp(){
		if(appType.equals(APP_TYPE_FACILITATOR)){
			return true;
		}
		return false;
	}
	public boolean isCustomerApp(){
		if(appType.equals(APP_TYPE_CUSTOMER)){
			return true;
		}
		return false;
	}
	public boolean isRenterWapApp(){
		if(appType.equals(APP_TYPE_RENTER)){
			return true;
		}
		return false;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getPbrand() {
		return pbrand;
	}

	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppPkg() {
		return appPkg;
	}
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}
}
