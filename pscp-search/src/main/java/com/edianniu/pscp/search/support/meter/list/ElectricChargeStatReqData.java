/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.util.Set;

/**
 * MonthLogReqData
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月4日 下午4:45:48
 * @version V1.0
 */
public class ElectricChargeStatReqData extends BaseStatReqData {
	private static final long serialVersionUID = 1L;
	private Long companyId;
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	private Set<String> meterIds;
	public Set<String> getMeterIds() {
		return meterIds;
	}
	public void setMeterIds(Set<String> meterIds) {
		this.meterIds = meterIds;
	}
}
