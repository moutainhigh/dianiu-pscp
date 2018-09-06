package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.mis.bean.Result;

public class PayOrderListResult  extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer nextOffset;
	
	private Integer totalCount;
	
	private Boolean hasNext;
	
	private List<PayOrderInfo> orderInfos;
	
	public List<PayOrderInfo> getOrderInfos() {
		return orderInfos;
	}
	public void setOrderInfos(List<PayOrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}
	public Integer getNextOffset() {
		return nextOffset;
	}
	public void setNextOffset(Integer nextOffset) {
		this.nextOffset = nextOffset;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Boolean getHasNext() {
		return hasNext;
	}
	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}
}
