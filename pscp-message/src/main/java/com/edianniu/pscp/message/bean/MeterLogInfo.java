/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:33:29 
 * @version V1.0
 */
package com.edianniu.pscp.message.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.media.jfxmedia.logging.Logger;

/**
 * 仪表日志
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:33:29
 * @version V1.0
 */
public class MeterLogInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String meterId;// 仪表编号
	private Integer type = 1;// 仪表类型 1默认是netdau
	private String subTermCode;// 分项编码
	private Date reportTime;// 上报时间
	private Map<String, Object> data = new HashMap<>();// 仪表数据
	/**
	 * key 字段名 value coding coding 192是正常的，不会存在这里，其他参数会保存到这里去的。
	 * 
	 */
	private Map<String, String> errors = new HashMap<>();// 仪表错误数据

	public boolean isSuccessData() {
		String valueNames[] = { "abcActivePower", "aActivePower",
				"abcPowerFactor", "aPowerFactor", "ua", "ia",
				"positiveTotalActivePowerCharge",
				"positiveApexActivePowerCharge", "positivePeakPowerCharge",
				"positiveFlatPowerCharge", "positiveValleyPowerCharge",
				"negativeTotalActivePowerCharge",
				"negativeApexActivePowerCharge", "negativePeakPowerCharge",
				"negativeFlatPowerCharge", "negativeValleyPowerCharge",
				"positiveTotalReactivePowerCharge",
				"positiveApexReactivePowerCharge",
				"positivePeakReactivePowerCharge",
				"positiveFlatReactivePowerCharge",
				"positiveValleyReactivePowerCharge",
				"negativeTotalReactivePowerCharge",
				"negativePeakReactivePowerCharge",
				"negativeFlatReactivePowerCharge",
				"negativeValleyReactivePowerCharge" };
		for (String valueName : valueNames) {
			if (!isValueSuccess(valueName)) {
				return false;
			}
		}
		return true;
	}
	public boolean isValueSuccess(String valueName) {
		Object o = getData().get(valueName);
		if (o == null) {
			return false;
		}
		if(o instanceof Double){
			System.out.println("value:"+(Double) o);
			if (((Double) o).equals(-1.00000D)) {
				return false;
			}
		}
		return true;
	}

	public void setValue(String valueName, String value) {
		getData().put(valueName, value);
	}

	public String getMeterId() {
		return meterId;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getSubTermCode() {
		return subTermCode;
	}

	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
