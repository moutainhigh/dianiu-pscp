package com.edianniu.pscp.renter.mis.bean.renter.vo;

import java.io.Serializable;

/**
 * 租客用电数据
 * @author zhoujianjian
 * @date 2018年4月8日 下午2:17:18
 */
public class RenterDataVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long renterId;
	
	private String name;
	
	private String address;
	
	private String quantity;
	
	private String charge;
	// yyyy或yyyy-MM - yyyy-MM或yyyy-MM-dd
	private String time;
	public Long getRenterId() {
		return renterId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
