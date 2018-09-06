/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月2日 下午12:28:07 
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.electrician;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月2日 下午12:28:07 
 * @version V1.0
 */
public class ElectricianInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> certificates;
	public List<Map<String, Object>> getCertificates() {
		return certificates;
	}
	public void setCertificates(List<Map<String, Object>> certificates) {
		this.certificates = certificates;
	}
}
