/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.das.meter.dao;

import com.edianniu.pscp.das.meter.domain.MeterLog;


/**
 * 企业仪表信息
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
public interface MeterLogDao {
	public int save(MeterLog meterLog);
}
