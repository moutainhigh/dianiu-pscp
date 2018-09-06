/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午7:25:58 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午7:25:58 
 * @version V1.0
 */
public class DividedElectricVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String subItemCode;
	private Double electric;
	public String getSubItemCode() {
		return subItemCode;
	}
	public Double getElectric() {
		return electric;
	}
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}
	public void setElectric(Double electric) {
		this.electric = electric;
	}

}
