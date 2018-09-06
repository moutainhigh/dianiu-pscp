package com.edianniu.pscp.mis.query;

import com.edianniu.pscp.mis.commons.BaseQuery;

public class CarTravingTraceQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	private int offset; 	 
	private Integer type;//1:用户租车订单2:运营维护订单maintain维护历史轨迹rental租车历史轨迹ALL：全部
	private Long uid;
	private Long carId;
	private Long companyId;
	public int getOffset() {
		return offset;
	}
	public Integer getType() {
		return type;
	}
	public Long getUid() {
		return uid;
	}
	public Long getCarId() {
		return carId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
}
