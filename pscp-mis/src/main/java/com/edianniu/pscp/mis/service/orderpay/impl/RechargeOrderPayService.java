package com.edianniu.pscp.mis.service.orderpay.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.pay.CheckPayOrderResult;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;

/**
 * 充值订单支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:46:35 
 * @version V1.0
 */
public class RechargeOrderPayService implements OrderPayService {
	@Autowired
	private PayOrderService payOrderService;
	@Override
	public StartPayResult start(StartPayReq startPayReq) {
		StartPayResult result=new StartPayResult();
		DefaultResult validResult=validOrderIds(startPayReq.getOrderIds());
		result.set(validResult.getResultCode(), validResult.getResultMessage());
		return result;
	}
	@Override
	public PreparePayResult prepare(PreparePayReq preparePayReq) {
		PreparePayResult result=new PreparePayResult();
		DefaultResult validResult=validOrderIds(preparePayReq.getOrderIds());
		result.set(validResult.getResultCode(), validResult.getResultMessage());
		if(!result.isSuccess()){
			return result;
		}
		if (!PayType.include(preparePayReq.getPayType(),
                PayType.WALLET.getValue())) {
            result.setResultCode(ResultCode.ERROR_206);
            result.setResultMessage("payType 必须为1,2,3");
            return result;
        }
		return result;
	}
	private DefaultResult validOrderIds(String orderIds) {
		DefaultResult result=new DefaultResult();
		if (StringUtils.isNoneBlank(orderIds)) {
            if (StringUtils.split(orderIds, ",").length > 0) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("orderIds 格式错误");
                return result;
            }
            if (payOrderService.existOrderId(orderIds)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("orderIds 重复");
                return result;
            }
        }
		return result;
	}
	@Override
	public boolean updateOrderStatus(PayOrder payOrder) {
		//忽略
		return true;
		
	}
	@Override
	public double getOrderAmount(Integer orderType, String orderId) {
		//忽略
		return 0;
	}
	@Override
	public boolean updateSyncOrderStatus(PayOrder payOrder) {
		//忽略
		return false;
	}
	@Override
	public boolean updateUserWallet(PayOrder payOrder) {
		//忽略
		return false;
	}
	@Override
	public boolean addUserWalletDetail(PayOrder payOrder) {
		//忽略
		return false;
	}
	@Override
	public CheckPayOrderResult checkPayOrder(PayOrder payOrder,PayPush payPush) {
		//忽略
		return new CheckPayOrderResult();
	}

}
