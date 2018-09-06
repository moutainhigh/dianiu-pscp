package com.edianniu.pscp.mis.service.orderpay;

import com.edianniu.pscp.mis.bean.pay.CheckPayOrderResult;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.exception.BusinessException;

/**
 * 具体业务的支付情况
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:35:52 
 * @version V1.0
 */
public interface OrderPayService {
	/**
	 * 开始支付
	 * @param startPayReq
	 * @return
	 */
    public StartPayResult start(StartPayReq startPayReq);
    /**
     * 预支付
     * @param preparePayReq
     * @return
     */
    public PreparePayResult prepare(PreparePayReq preparePayReq);
    /**
     * 更新业务订单支付状态或者状态-异步更新
     * @param payOrder
     */
    public boolean updateOrderStatus(PayOrder payOrder);
    /**
     * 更新业务订单支付状态或者状态-同步更新
     * @param payOrder
     */
    public boolean updateSyncOrderStatus(PayOrder payOrder);
    /**
     * 批量支付时获取每个自订单的实际支付金额
     * @param orderType
     * @param orderId
     * @return
     */
    public double getOrderAmount(Integer orderType,String orderId);
    /**
     * 更新钱包信息
     * @param payOrder
     * @return
     */
    public boolean updateUserWallet(PayOrder payOrder);
    /**
     * 更新钱包明细信息
     * @param payOrder
     * @return
     */
    public boolean addUserWalletDetail(PayOrder payOrder);
    /**
     * 异步通知时检查支付订单
     * 1)是否异常支付订单
     * 2)是否重复支付订单
     * @param payOrder
     * @param payPush
     * @return
     */
    public CheckPayOrderResult checkPayOrder(PayOrder payOrder,PayPush payPush) throws BusinessException;
    
    
}
