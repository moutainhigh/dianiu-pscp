package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.PayOrder;

/**
 * NeedsOrderService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午2:46:14 
 * @version V1.0
 */
public interface NeedsOrderService {
	/**
	 * 根据id获取需求订单
	 * @param id
	 * @return
	 */
	public NeedsOrder getById(Long id);
	/**
	 * 根据needsId和companyId获取需求订单
	 * @param needsId
	 * @param companyId
	 * @return
	 */
	public NeedsOrder getByNeedsIdAndCompanyId(Long needsId,Long companyId);
	/**
	 *  根据companyId orderId 获取需求订单
	 * @param companyId
	 * @param orderId
	 * @return
	 */
	public NeedsOrder getNeedsOrder(Long companyId,String orderId);
	/**
	 * 根据orderId获取需求订单
	 * @param orderId
	 * @return
	 */
	public NeedsOrder getNeedsOrderByOrderId(String orderId);
	
	/**
	 * 更新需求订单
	 * @param needsOrder
	 */
	public int update(NeedsOrder needsOrder);
	/**
	 * 更新需求订单保证金退款状态
	 * @param needsOrder
	 */
	public int updateRefundStatus(NeedsOrder needsOrder);
	/**
	 * 需求订单保证金退款
	 * @param needsOrder
	 * @param isProjectSettle true/false
	 * @return
	 */
	public boolean refundNeedsOrderDeposit(NeedsOrder needsOrder,boolean isProjectSettle);
	/**
	 * 是否存在需求订单Id
	 * @param orderId
	 * @return
	 */
	public boolean existOrderId(String orderId);
	
	public boolean updatePayStatus(PayOrder payOrder);

}
