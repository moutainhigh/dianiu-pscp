package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:30:26
 * @version V1.0
 */
public class SocialWorkOrderListResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
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
