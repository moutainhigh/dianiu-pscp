package com.edianniu.pscp.cs.bean.response.power;
import java.util.List;

import com.edianniu.pscp.cs.bean.power.QuantitiesOfMonth;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午8:02:15
 */
@JSONMessage(messageCode = 2002183)
public class PowerQuantityResponse extends BaseResponse{
	
	private String maxQuantityOfThisMonth;
	
	private String maxQuantityOfLastMonth;
	
	private List<QuantitiesOfMonth> quantitiesOfThisMonths;
	
	private List<QuantitiesOfMonth> quantitiesOfLastMonths;

	public String getMaxQuantityOfThisMonth() {
		return maxQuantityOfThisMonth;
	}

	public void setMaxQuantityOfThisMonth(String maxQuantityOfThisMonth) {
		this.maxQuantityOfThisMonth = maxQuantityOfThisMonth;
	}

	public String getMaxQuantityOfLastMonth() {
		return maxQuantityOfLastMonth;
	}

	public void setMaxQuantityOfLastMonth(String maxQuantityOfLastMonth) {
		this.maxQuantityOfLastMonth = maxQuantityOfLastMonth;
	}

	public List<QuantitiesOfMonth> getQuantitiesOfThisMonths() {
		return quantitiesOfThisMonths;
	}

	public void setQuantitiesOfThisMonths(List<QuantitiesOfMonth> quantitiesOfThisMonths) {
		this.quantitiesOfThisMonths = quantitiesOfThisMonths;
	}

	public List<QuantitiesOfMonth> getQuantitiesOfLastMonths() {
		return quantitiesOfLastMonths;
	}

	public void setQuantitiesOfLastMonths(List<QuantitiesOfMonth> quantitiesOfLastMonths) {
		this.quantitiesOfLastMonths = quantitiesOfLastMonths;
	}
	
}
