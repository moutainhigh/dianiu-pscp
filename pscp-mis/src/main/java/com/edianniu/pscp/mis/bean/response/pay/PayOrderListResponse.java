package com.edianniu.pscp.mis.bean.response.pay;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;
import com.edianniu.pscp.mis.bean.pay.PayOrderInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;


@JSONMessage(messageCode = 2002202)
public class PayOrderListResponse  extends BaseResponse {
	
	private List<PayOrderInfo> orderInfos;
	
    private Integer nextOffset;
	
	private Integer totalCount;
	
	private Boolean hasNext;
	
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


	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
