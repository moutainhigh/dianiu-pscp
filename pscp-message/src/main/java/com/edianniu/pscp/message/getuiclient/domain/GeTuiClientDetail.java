/**
 * 
 */
package com.edianniu.pscp.message.getuiclient.domain;

import java.util.Date;

import com.edianniu.pscp.message.commons.BaseDo;

/**
 * @author cyl
 *
 */
public class GeTuiClientDetail extends BaseDo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long uid;
	private String osType;
	private String appType;
	private String appPkg;
	private String clientId;
	private String deviceToken;	
	private Long companyId;
	private Date reportTime;
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
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getAppType() {
		return appType;
	}
	public String getAppPkg() {
		return appPkg;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}

}
