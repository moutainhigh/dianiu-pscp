package com.edianniu.pscp.mis.util.wxpay;

import java.io.Serializable;

public class H5Info implements Serializable{
	private static final long serialVersionUID = 1L;
	private String type="Wap";
	private String wap_url;
	private String wap_name;
	public String getType() {
		return type;
	}
	public String getWap_url() {
		return wap_url;
	}
	public String getWap_name() {
		return wap_name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setWap_url(String wap_url) {
		this.wap_url = wap_url;
	}
	public void setWap_name(String wap_name) {
		this.wap_name = wap_name;
	}
}
