package com.edianniu.pscp.portal.dao;

import com.edianniu.pscp.portal.entity.ElectricianEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:10:04
 */
public interface ElectricianDao extends BaseDao<ElectricianEntity> {
	public  ElectricianEntity getByUid(Long uid);
}
