package com.edianniu.pscp.mis.bean.wallet;
import java.io.Serializable;

public class DaybookReqData  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long uid;
    private int offset;
    private int limit;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

    
}
