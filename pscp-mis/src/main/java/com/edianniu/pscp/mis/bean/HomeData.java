package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

public class HomeData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private String nodesVersion;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNodesVersion() {
		return nodesVersion;
	}

	public void setNodesVersion(String nodesVersion) {
		this.nodesVersion = nodesVersion;
	}

	
	

}
