/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * 日功率因数
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayPowerFactorListReqData extends BaseListReqData implements
		Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// yyyyMMdd

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
