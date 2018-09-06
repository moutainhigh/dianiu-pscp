/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:59:39 
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.workorder.electrician;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:59:39
 * @version V1.0
 */
public class ListResult extends Result {
	
	private static final long serialVersionUID = 1L;
	
	List<ElectricianWorkOrderInfo> electricianWorkOrders;
	private int nextOffset;
	private int totalCount;
	private boolean hasNext;

	public List<ElectricianWorkOrderInfo> getElectricianWorkOrders() {
		return electricianWorkOrders;
	}

	public int getNextOffset() {
		return nextOffset;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setElectricianWorkOrders(
			List<ElectricianWorkOrderInfo> electricianWorkOrders) {
		this.electricianWorkOrders = electricianWorkOrders;
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
}
