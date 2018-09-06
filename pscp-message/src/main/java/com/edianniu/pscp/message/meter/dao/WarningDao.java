package com.edianniu.pscp.message.meter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.edianniu.pscp.message.meter.domain.Warning;

/**
 * 客户设备告警
 * @author zhoujianjian
 * @date 2018年3月5日 下午3:08:16
 */
@Mapper
public interface WarningDao {

	void save(Warning warning);

	void updateDealStatus(Warning warning);
	
	

}
