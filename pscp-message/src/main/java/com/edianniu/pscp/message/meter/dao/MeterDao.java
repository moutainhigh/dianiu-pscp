/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.message.meter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.message.meter.domain.Meter;

/**
 * 企业仪表信息
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
@Mapper
public interface MeterDao {
	public Meter getById(Long id);
	public Meter get(@Param("buildingId")String buildingId,
			@Param("gatewayId")String gatewayId,@Param("meterId")String meterId,@Param("type")Integer type);
	public int save(Meter meter);
	public int update(Meter meter);
	public int updateStatus(Meter meter);
	public List<Meter> getList(@Param("buildingId")String buildingId,
			@Param("gatewayId")String gatewayId,@Param("type")Integer type);
}
