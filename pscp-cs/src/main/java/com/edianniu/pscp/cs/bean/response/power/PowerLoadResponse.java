package com.edianniu.pscp.cs.bean.response.power;

import com.edianniu.pscp.cs.bean.power.vo.LoadVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 用电负荷
 * @author zhoujianjian
 * @date 2017年12月7日 下午6:55:44
 */
@JSONMessage(messageCode = 2002182)
public class PowerLoadResponse extends BaseResponse{
	
	private LoadVO load;
	
	private String quantityOfThisMonth;
	
	private String chargeOfThisMonth;
	
	private String maxLoadOfToday;
	
	private String maxLoadOfLastDay;
	
	private String powerFactor ;
	
	private Integer frequency;

	public LoadVO getLoad() {
		return load;
	}

	public void setLoad(LoadVO load) {
		this.load = load;
	}

	public String getQuantityOfThisMonth() {
		return quantityOfThisMonth;
	}

	public void setQuantityOfThisMonth(String quantityOfThisMonth) {
		this.quantityOfThisMonth = quantityOfThisMonth;
	}

	public String getChargeOfThisMonth() {
		return chargeOfThisMonth;
	}

	public void setChargeOfThisMonth(String chargeOfThisMonth) {
		this.chargeOfThisMonth = chargeOfThisMonth;
	}

	public String getMaxLoadOfToday() {
		return maxLoadOfToday;
	}

	public void setMaxLoadOfToday(String maxLoadOfToday) {
		this.maxLoadOfToday = maxLoadOfToday;
	}

	public String getMaxLoadOfLastDay() {
		return maxLoadOfLastDay;
	}

	public void setMaxLoadOfLastDay(String maxLoadOfLastDay) {
		this.maxLoadOfLastDay = maxLoadOfLastDay;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
}
