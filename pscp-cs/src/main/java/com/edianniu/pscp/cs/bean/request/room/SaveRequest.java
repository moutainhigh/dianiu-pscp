package com.edianniu.pscp.cs.bean.request.room;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:10:07
 */
@JSONMessage(messageCode = 1002119)
public class SaveRequest extends TerminalRequest {
	
	//配电房主键
	private Long id;
	
	//配电房名称
	private String name;
	
	//负责人
	private String director;
	
	//联系方式
	private String contactNumber;
	
	//地址
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	 public String toString() {
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	 }

}
