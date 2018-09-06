package com.edianniu.pscp.renter.mis.bean.renter;

import java.util.List;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.OrderVO;

public class OrderDetailResult extends Result{

	private static final long serialVersionUID = 1L;
	
	// 计费方式：0:统一单价   1:分时计费
	private Integer subChargeMode;
	// 账单详情
	private OrderVO bill;
	// 计费项目
	private List<CountItem> countItems;
	// 分项用电
	private List<UseType> useTypes;
	public Integer getSubChargeMode() {
		return subChargeMode;
	}
	public void setSubChargeMode(Integer subChargeMode) {
		this.subChargeMode = subChargeMode;
	}
	public OrderVO getBill() {
		return bill;
	}
	public void setBill(OrderVO bill) {
		this.bill = bill;
	}
	public List<CountItem> getCountItems() {
		return countItems;
	}
	public void setCountItems(List<CountItem> countItems) {
		this.countItems = countItems;
	}
	public List<UseType> getUseTypes() {
		return useTypes;
	}
	public void setUseTypes(List<UseType> useTypes) {
		this.useTypes = useTypes;
	}
	
	

}
