/**
 *
 */
package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.PayOrder;

/**
 * @author cyl
 */
public interface PayOrderDao {
    public PayOrder getById(Long id);

    public PayOrder getUidAndOrderId(@Param("uid") Long uid, @Param("orderId") String orderId);

    /**
     * 根据uid和orderIds(业务编号)
     *
     * @param uid
     * @param orderIds
     * @return
     */
    public PayOrder getUidAndOrderIds(@Param("uid") Long uid, @Param("orderIds") String orderIds);

    public PayOrder getByOrderId(@Param("orderId") String orderId);

    /**
     * 根据发票编号获取订单
     *
     * @param invoiceOrderId
     * @return
     */
    public PayOrder getByInvoiceOrderId(@Param("invoiceOrderId") String invoiceOrderId);

    public int getCountByOrderId(String orderId);

    public void save(PayOrder payOrder);

    public int updatePayStatus(PayOrder payOrder);

    public int update(PayOrder payOrder);

    public int updateInvoiceOrderId(PayOrder payOrder);

	public List<PayOrder> queryPayOrderList(@Param("uid")Long uid, @Param("type")Integer type, 
			@Param("status")Integer status, @Param("offset")Integer offset, @Param("limit")Integer limit);
	
	public int queryPayOrderListCount(@Param("uid")Long uid, @Param("type")Integer type, 
			@Param("status")Integer status);

}
