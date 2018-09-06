package com.edianniu.pscp.cms.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.cms.entity.AppCheckUpdateEntity;

/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 10:08:40
 */
public interface AppCheckUpdateDao extends BaseDao<AppCheckUpdateEntity> {

	Integer getMaxAppLatestVer(@Param("appPkg")String appPkg);
	
}
