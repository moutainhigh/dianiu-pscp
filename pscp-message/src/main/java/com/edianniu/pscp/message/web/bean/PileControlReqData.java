/**
 * 
 */
package com.edianniu.pscp.message.web.bean;

import java.io.Serializable;

/**
 * @author cyl
 *
 */
public class PileControlReqData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pileCode;
	private String cmd;
	public String getPileCode() {
		return pileCode;
	}
	public void setPileCode(String pileCode) {
		this.pileCode = pileCode;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
