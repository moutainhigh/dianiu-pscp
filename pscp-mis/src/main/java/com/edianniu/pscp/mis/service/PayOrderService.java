/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午3:22:18
 * @version V1.0
 */
package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.pay.PayOrderInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.PayOrder;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午3:22:18
 */
public interface PayOrderService {
    /**
     * 根据id获取支付订单
     *
     * @param id
     * @return
     */
    public PayOrder getById(Long id);

    /**
     * 根据uid orderId 获取支付订单
     *
     * @param uid
     * @param orderId
     * @return
     */
    public PayOrder getPayOrder(Long uid, String orderId);

    /**
     * 根据发票编号 获取支付订单
     *
     * @param invoiceOrderId
     * @return
     */
    public PayOrder getPayOrderByInvoiceOrderId(String invoiceOrderId);

    /**
     * 根据uid orderIds 获取支付订单
     *
     * @param uid
     * @param orderIds
     * @return
     */
    public PayOrder getPayOrderByUidAndOrderIds(Long uid, String orderIds);

    /**
     * 根据订单ID和订单类型获取支付订单信息
     *
     * @param uid
     * @param orderId      支付订单编号
     * @param orderIds     业务订单编号，支持批量
     * @param amount
     * @param orderType
     * @param payType
     * @param payMethod
     * @param payChannel
     * @param needInvoice
     * @param extendParams
     * @return
     */
    public PayOrder buildPayOrder(Long uid, String orderId, String orderIds, Double amount,
                                  Integer orderType, Integer payType, String payMethod, String payChannel, Integer needInvoice, String extendParams);

    /**
     * 根据orderId获取支付订单
     *
     * @param orderId
     * @return
     */
    public PayOrder getPayOrderByOrderId(String orderId);

    /**
     * 保存支付订单
     *
     * @param payOrder
     */
    public void save(PayOrder payOrder);

    /**
     * 更新支付订单
     *
     * @param payOrder
     * @return
     */
    public int update(PayOrder payOrder);

    /**
     * 更新支付订单发票信息
     *
     * @param payOrder
     * @return
     */
    public int updateInvoiceOrderId(PayOrder payOrder);

    /**
     * 更新支付订单
     *
     * @param payOrder
     */
    public int updatePayStatus(PayOrder payOrder);

    /**
     * 是否存在支付订单Id
     *
     * @param orderId
     * @return
     */
    public boolean existOrderId(String orderId);

    /**
     * 查询支付订单列表
     * @param uid
     * @param type
     * @param status
     * @param limit 
     * @param offset 
     * @return
     */
	public PageResult<PayOrderInfo> queryPayOrderList(Long uid, Integer type, Integer status, Integer offset, Integer limit);


}
