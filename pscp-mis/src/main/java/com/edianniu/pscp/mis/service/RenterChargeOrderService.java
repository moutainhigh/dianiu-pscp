package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.CompanyRenterConfig;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.mis.domain.UserWallet;

/**
 * RenterChargeOrderService
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月29日 下午2:46:14
 */
public interface RenterChargeOrderService {
    /**
     * 根据id获取订单
     *
     * @param id
     * @return
     */
    public RenterChargeOrder getById(Long id);

    /**
     * 根据orderId获取订单
     *
     * @param orderId
     * @return
     */
    public RenterChargeOrder getByOrderId(String orderId);


    /**
     * 根据payOrderId获取订单
     *
     * @param payOrderId
     * @return
     */
    public RenterChargeOrder getByPayOrderId(String payOrderId);

    /**
     * 获取电费单房东UID
     *
     * @param orderId
     * @return
     */
    public Long getCompanyUidByOrderId(String orderId);

    /**
     * 更新订单
     *
     * @param renterChargeOrder
     */
    public int update(RenterChargeOrder renterChargeOrder);

    /**
     * 是否存在订单Id
     *
     * @param orderId
     * @return
     */
    public boolean existOrderId(String orderId);

    public boolean updatePayStatus(PayOrder payOrder);
    /**
     * 预付费账单结算
     * @param orderId
     * @return
     */
    public boolean settlementPrepayRenterChargeOrder(String orderId);
    
    public void walletNotify(final UserWallet wallet,final CompanyRenterConfig renterConfig);

}
