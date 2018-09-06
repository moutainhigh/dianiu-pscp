package com.edianniu.pscp.renter.mis.bean.request.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.renter.mis.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客开合闸
 * @author zhoujianjian
 * @date 2018年4月4日 上午9:17:09
 */
@JSONMessage(messageCode = 1002306)
public class SwitchRequest extends TerminalRequest{
	
	private Long renterId;
	
	private Long meterId;
	
	private Integer type;
	
	private String pwd;
	
	public Long getRenterId() {
		return renterId;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
