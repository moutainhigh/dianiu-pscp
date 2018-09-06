package com.edianniu.pscp.mis.bean.request;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 1002012)
public class HomeRequest extends TerminalRequest{
	
	private String nodesVersion;

	

	public String getNodesVersion() {
		return nodesVersion;
	}

	public void setNodesVersion(String nodesVersion) {
		this.nodesVersion = nodesVersion;
	}
	
}
