/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月13日 上午11:54:40 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月13日 上午11:54:40 
 * @version V1.0
 */
public class FeedbackReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String content;
	private Long uid;
	private String token;
	private String version;
	private String showVersion;
	private String did;
	private String osType;
	private String osVersion;
	private String pbrand;
	private String ptype;
	private String screenWidth;
	private String screenHeight;
	private String networkType;
	private String ramSize;
	private String romSize;
	private String clientId; 
	private String deviceToken;
	private String appPkg;
	private String clientIp;
	public String getContent() {
		return content;
	}
	public Long getUid() {
		return uid;
	}
	public String getToken() {
		return token;
	}
	public String getVersion() {
		return version;
	}
	public String getShowVersion() {
		return showVersion;
	}
	public String getDid() {
		return did;
	}
	public String getOsType() {
		return osType;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public String getPbrand() {
		return pbrand;
	}
	public String getPtype() {
		return ptype;
	}
	public String getScreenWidth() {
		return screenWidth;
	}
	public String getScreenHeight() {
		return screenHeight;
	}
	public String getNetworkType() {
		return networkType;
	}
	public String getRamSize() {
		return ramSize;
	}
	public String getRomSize() {
		return romSize;
	}
	public String getClientId() {
		return clientId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public String getAppPkg() {
		return appPkg;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setShowVersion(String showVersion) {
		this.showVersion = showVersion;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public void setScreenWidth(String screenWidth) {
		this.screenWidth = screenWidth;
	}
	public void setScreenHeight(String screenHeight) {
		this.screenHeight = screenHeight;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}
	public void setRomSize(String romSize) {
		this.romSize = romSize;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
