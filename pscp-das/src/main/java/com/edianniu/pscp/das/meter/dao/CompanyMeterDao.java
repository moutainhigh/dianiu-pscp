/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.das.meter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.das.meter.domain.CompanyMeter;
import com.edianniu.pscp.das.meter.domain.MeterConfig;


/**
 * 企业仪表信息
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
public interface CompanyMeterDao {
	/**
	 * CompanyMeter 
	 * @param meterId
	 * @return
	 */
	public CompanyMeter getByMeterId(@Param("meterId")String meterId);
	/**
	 * 获取配置信息
	 * @param companyId
	 * @param type
	 * @return
	 */
	public List<MeterConfig> getConfigsByCompanyId(@Param("companyId")Long companyId,
			@Param("type")Integer type);
	
}
