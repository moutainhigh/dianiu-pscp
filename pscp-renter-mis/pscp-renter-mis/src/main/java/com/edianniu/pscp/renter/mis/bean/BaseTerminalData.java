/**
 * 
 */
package com.edianniu.pscp.renter.mis.bean;

import java.io.Serializable;

/**
 * @author cyl
 *
 */
public class BaseTerminalData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String clientId; 
	private String deviceToken;
	private int terminalType;
	private String osType;
	private String did;
	private String pbrand;
	private String ptype;
	private String version;
	public String getClientId() {
		return clientId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public int getTerminalType() {
		return terminalType;
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
	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
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
}
