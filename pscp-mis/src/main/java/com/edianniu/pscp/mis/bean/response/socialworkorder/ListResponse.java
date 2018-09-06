package com.edianniu.pscp.mis.bean.response.socialworkorder;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002014)
public final class ListResponse extends BaseResponse {
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;
	private List<SocialWorkOrderInfo> workOrders;

	public int getNextOffset() {
		return nextOffset;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public List<SocialWorkOrderInfo> getWorkOrders() {
		return workOrders;
	}

	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setWorkOrders(List<SocialWorkOrderInfo> workOrders) {
		this.workOrders = workOrders;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
