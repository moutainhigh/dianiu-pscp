/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * 需量明细 list
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DemandDetailListReqData extends BaseListReqData implements
		Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// yyyyMM
	private Integer type = 1;// 1 区间算法,2滑差算法
	private Integer value = 15;// 1 区间间隔时间，2滑差时间

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getType() {
		return type;
	}

	public Integer getValue() {
		return value;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
