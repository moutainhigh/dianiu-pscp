/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07 
 * @version V1.0
 */
package com.edianniu.pscp.message.meter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.message.meter.domain.GateWay;
/**
 * netdau gateway
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 下午9:25:07
 * @version V1.0
 */
@Mapper
public interface GateWayDao {
	public GateWay getById(Long id);
	public GateWay get(@Param("buildingId")String buildingId,
			@Param("gatewayId")String gatewayId);
	public int save(GateWay gateWay);
	public int update(GateWay gateWay);
	public int updateStatus(GateWay gateWay);
}
