/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.das.meter.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;

/**
 * 企业电价配置
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
public interface CompanyPowerPriceConfigDao {
	/**
	 * 获取企业电价配置信息
	 * @param companyId
	 * @return
	 */
	public CompanyPowerPriceConfig getByCompanyId(@Param("companyId")Long companyId);
}
