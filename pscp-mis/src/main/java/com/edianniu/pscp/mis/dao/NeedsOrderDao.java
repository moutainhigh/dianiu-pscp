package com.edianniu.pscp.mis.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.NeedsOrder;

/**
 * NeedsOrderDao
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午2:35:25 
 * @version V1.0
 */
public interface NeedsOrderDao extends BaseDao<NeedsOrder>{
    public NeedsOrder getById(Long id);
    public NeedsOrder getByNeedsIdAndCompanyId(@Param("needsId")Long needsId,@Param("companyId")Long companyId);
	public NeedsOrder getCompanyIdAndOrderId(@Param("companyId")Long companyId,@Param("orderId")String orderId);
	public NeedsOrder getByOrderId(@Param("orderId")String orderId);
	public int getCountByOrderId(String orderId);
	public int update(NeedsOrder needsOrder);
	public int updateRefundStatus(NeedsOrder needsOrder);
	
}
