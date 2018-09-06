package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

/**
 * 
 * @author cyl
 *
 */
public class CheckAppUpdateReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private boolean isLogin;
	private String secretkey;
	private String appPkg;	
	private Long appVer;
	private String osType;
	private String osVer;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public String getAppPkg() {
		return appPkg;
	}
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}
	public Long getAppVer() {
		return appVer;
	}
	public void setAppVer(Long appVer) {
		this.appVer = appVer;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsVer() {
		return osVer;
	}
	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}
	
	

}
