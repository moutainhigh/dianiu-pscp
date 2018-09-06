package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * DayLoadDetailListReqData
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午12:22:16
 * @version V1.0
 */
public class DayLoadDetailListReqData extends BaseListReqData implements
		Serializable {
	private static final long serialVersionUID = -101315817790932729L;
	private String date;// yyyyMMdd

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
