/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月16日 下午4:19:22 
 * @version V1.0
 */
package com.edianniu.pscp.renter.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.renter.mis.bean.Result;

/**
 * RenterOrderInfoService
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月16日 下午4:19:22
 * @version V1.0
 */
public interface RenterOrderInfoService {
	/**
	 * 获取未结算预付费账单列表
	 * @param limit
	 * @return
	 */
	public List<String> getPrePaymentUnsettledOrders(int limit);
	/**
	 * 结算预付费账单
	 * 结算时需要判断当前账单是否已截止：
	 *   1)已截止直接可使用余额结算
	 *   2)未截止需要判断当前账单任务状态是否为0 为0可结算，为1不可结算(防止数据冲突)
	 *   3)结算后，需要调用用户钱包接口，还需要判断当前订单总的扣款余额是否正常
	 * @param orderId
	 * @return
	 *//*
	public Result settlementPrepaymentOrder(String orderId);*/
	
	public Result beforeHandleOrder(Long renterId);
	/**
	 * 处理预付费账单
	 * @param renterId
	 */
	public Result handlePrePaymentOrder(Long renterId);
	/**
	 * 处理月结算账单
	 * @param renterId
	 */
	public Result handleMonthlyPaymentOrder(Long renterId);
	
	public Result afterHandlerOrder(Long renterId);
}
