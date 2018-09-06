package com.edianniu.pscp.cs.bean.response.power;

import java.util.List;
import com.edianniu.pscp.cs.bean.power.Charge;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 电费单
 * @author zhoujianjian
 * @date 2017年12月8日 下午4:47:08
 */
@JSONMessage(messageCode = 2002186)
public class ChargeBillResponse extends BaseResponse{
	// 电费周期
	private String period;
	// 当月电费合计（保留两位小数）
	private String totalChargeOfThisMonth;
	// 奖励金（保留两位小数）
	private String award;
	// 电费明细
	private List<Charge> charges;
	// 电度电费计费方式(0普通       1分时)
	private Integer chargeMode;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTotalChargeOfThisMonth() {
		return totalChargeOfThisMonth;
	}

	public void setTotalChargeOfThisMonth(String totalChargeOfThisMonth) {
		this.totalChargeOfThisMonth = totalChargeOfThisMonth;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	
	
}
