package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;
/**
 * 分项能耗请求
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午8:03:09 
 * @version V1.0
 */
public class SubEnergyReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean needSubEnergy=false;
	private String[] meterIds;
	public String[] getMeterIds() {
		return meterIds;
	}
	
	public void setMeterIds(String[] meterIds) {
		this.meterIds = meterIds;
	}

	public boolean isNeedSubEnergy() {
		return needSubEnergy;
	}

	public void setNeedSubEnergy(boolean needSubEnergy) {
		this.needSubEnergy = needSubEnergy;
	}
}
