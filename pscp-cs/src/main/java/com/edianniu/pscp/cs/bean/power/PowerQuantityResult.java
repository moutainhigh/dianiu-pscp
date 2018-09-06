package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午8:11:47
 */
public class PowerQuantityResult extends Result{

	private static final long serialVersionUID = 1L;
	
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
