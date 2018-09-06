package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class PayConfirmResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isAsync=false;
	private boolean isRepeat=false;
	public boolean isAsync() {
		return isAsync;
	}
	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}
	public boolean isRepeat() {
		return isRepeat;
	}
	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
}
