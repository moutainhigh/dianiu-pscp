package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WithdrawalscashAuditReqData  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id ;
	private Integer status;
	private String opUser;
	private String memo;
	public Long getId() {
		return id;
	}
	public Integer getStatus() {
		return status;
	}
	public String getOpUser() {
		return opUser;
	}
	public String getMemo() {
		return memo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
