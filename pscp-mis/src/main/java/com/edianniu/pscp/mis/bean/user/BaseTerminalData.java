/**
 *
 */
package com.edianniu.pscp.mis.bean.user;

import org.apache.commons.lang3.StringUtils;

import com.edianniu.pscp.mis.bean.AppPackage;

import java.io.Serializable;

/**
 * @author cyl
 */
public class BaseTerminalData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String clientId; 
	private String deviceToken;
	private String osType;
	private String did;
	private String pbrand;
	private String ptype;
	private String version;
	private String appPkg;
	public String getAppType(){
		if(isElectricianApp()){
			return "electrician";
		}
		else if(isFacilitatorApp()){
			return "facilitator";
		}
		else if(isCustomerApp()){
			return "customer";
		}
		else if(isRenterWapApp()){
			return "renter";
		}
		return "";
	}
	/**
	 * 是否webApp
	 * @return
	 */
	public boolean isWebApp(){//webAPP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.isWebApp(appPkg)){
			return true;
		}
		return false;
	}
	/**
	 * 是否renterWapAPP
	 * @return
	 */
	public boolean isRenterWapApp(){//retnerWapAPP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.isRenterWapApp(appPkg)){
			return true;
		}
		return false;
	}
	/**
	 * 是否电工APP
	 * @return
	 */
	public boolean isElectricianApp(){//电工APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.isElectricianApp(appPkg)){
			return true;
		}
		return false;
	}
	/**
	 * 是否服务商APP
	 * @return
	 */
	public boolean isFacilitatorApp(){//服务商APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.isFacilitatorApp(appPkg)){
			return true;
		}
		return false;
	}
	/**
	 * 是否客户APP
	 * @return
	 */
	public boolean isCustomerApp(){//客户APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.isCustomerApp(appPkg)){
			return true;
		}
		return false;
	}
	/**
	 * 是否原生APP
	 * @return
	 */
	public boolean isNativeApp(){
		if(this.isCustomerApp()||this.isElectricianApp()||this.isFacilitatorApp()){
			return true;
		}
		return false;
	}
	/**
	 * 检查appPkg
	 * @return
	 */
	public boolean checkAppPkg(){
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(AppPackage.include(appPkg)){
			return true;
		}
		return false;
	}
	public String getClientId() {
		return clientId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public String getOsType() {
		return osType;
	}
	public String getDid() {
		return did;
	}
	public String getPbrand() {
		return pbrand;
	}
	public String getPtype() {
		return ptype;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAppPkg() {
		return appPkg;
	}
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}

}
