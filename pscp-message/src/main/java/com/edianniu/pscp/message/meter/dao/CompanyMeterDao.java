/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.message.meter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.message.meter.domain.CompanyMeter;

/**
 * 企业仪表信息
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
@Mapper
public interface CompanyMeterDao {
	/**
	 * CompanyMeter 
	 * @param meterId
	 * @return
	 */
	public CompanyMeter getByMeterId(@Param("meterId")String meterId);
	/**
	 * 修改公司仪表状态
	 * @param companyMeter
	 * @return
	 */
	public int updateStatus(CompanyMeter companyMeter);
}
