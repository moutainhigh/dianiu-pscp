/**
 * 
 */
package com.edianniu.pscp.message.getuiclient.dao;

import com.edianniu.pscp.message.getuiclient.domain.GeTuiClientDetail;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.message.getuiclient.domain.GeTuiClient;

/**
 * @author cyl
 *
 */
@Mapper
public interface GeTuiClientDao {
	
	public GeTuiClient getById(Long id);

	public GeTuiClient getByUid(Long uid);
	
	public GeTuiClient getClientIdByUid(Long uid);
	
	public GeTuiClient getClientIdByUidAndCompanyId(@Param("uid")Long uid,@Param("companyId")Long companyId);

	public void save(GeTuiClient geTuiClient);

	public void update(GeTuiClient geTuiClient);
	
	public void saveDetail(GeTuiClientDetail geTuiClientDetail);
}
