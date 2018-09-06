/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午3:25:05
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayOrderInfo;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.PayOrderDao;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付订单服务类
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月18日 下午3:25:05
 */
@Service
@Repository("payOrderService")
public class DefaultPayOrderService implements PayOrderService {

    @Autowired
    private PayOrderDao payOrderDao;

    @Override
    public PayOrder getById(Long id) {

        return payOrderDao.getById(id);
    }

    @Override
    public PayOrder getPayOrder(Long uid, String orderId) {
        return payOrderDao.getUidAndOrderId(uid, orderId);
    }
    
	@Override
	public PageResult<PayOrderInfo> queryPayOrderList(Long uid, Integer type, Integer status, Integer offset, Integer limit) {
		PageResult<PayOrderInfo> result = new PageResult<>();
		int nextOffset = 0;
		int total = payOrderDao.queryPayOrderListCount(uid, type, status);
		if (total > 0) {
			List<PayOrder> payOrderList = payOrderDao.queryPayOrderList(uid, type, status, offset, limit);
			List<PayOrderInfo> payOrderInfos = new ArrayList<>();
    		for (PayOrder payOrder : payOrderList) {
    			PayOrderInfo payOrderInfo = new PayOrderInfo();
    			BeanUtils.copyProperties(payOrder, payOrderInfo);
    			payOrderInfo.setAmount(MoneyUtils.format(payOrder.getAmount()));
    			payOrderInfo.setPayTime(DateUtils.format(payOrder.getPayTime(), DateUtils.DATE_TIME_PATTERN));
    			payOrderInfo.setPayAsyncTime(DateUtils.format(payOrder.getPayAsyncTime(), DateUtils.DATE_TIME_PATTERN));
    			payOrderInfo.setPaySyncTime(DateUtils.format(payOrder.getPaySyncTime(), DateUtils.DATE_TIME_PATTERN));
    			payOrderInfos.add(payOrderInfo);
			}
			result.setData(payOrderInfos);
			nextOffset = offset + payOrderList.size();
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setTotal(total);
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		
		return result;
	}

    /**
     * 根据发票编号获取订单信息
     * @param invoiceOrderId
     * @return
     */
    @Override
    public PayOrder getPayOrderByInvoiceOrderId(String invoiceOrderId) {
        return payOrderDao.getByInvoiceOrderId(invoiceOrderId);
    }

    @Override
    public PayOrder getPayOrderByUidAndOrderIds(Long uid, String orderIds) {

        return payOrderDao.getUidAndOrderIds(uid, orderIds);
    }

    @Override
    public PayOrder getPayOrderByOrderId(String orderId) {

        return payOrderDao.getByOrderId(orderId);
    }

    @Override
    public void save(PayOrder payOrder) {
        payOrderDao.save(payOrder);
    }

    @Override
    public int update(PayOrder payOrder) {
        return payOrderDao.update(payOrder);
    }

    @Override
    public int updatePayStatus(PayOrder payOrder) {
        return payOrderDao.updatePayStatus(payOrder);

    }

    @Override
    @Transactional
    public PayOrder buildPayOrder(Long uid, String orderId, String orderIds, Double amount,
                                  Integer orderType, Integer payType, String payMethod, String payChannel, Integer needInvoice, String extendParams) {
        PayOrder order = new PayOrder();
        if (orderType == OrderType.RECHARGE.getValue()) {
            if (StringUtils.isBlank(orderIds)) {
                String orderId1 = BizUtils.getOrderId("CD");
                order.setOrderId(orderId1);
                order.setAssociatedOrderIds(orderId1);
            } else {
                order.setAssociatedOrderIds(orderIds);
                order.setOrderId(orderIds);
            }
        } else {
            if (StringUtils.isNoneBlank(orderId)) {//检查订单编号是否重复 TODO
                order.setOrderId(orderId);
                order.setAssociatedOrderIds(orderIds);
            } else {
                order.setOrderId(this.getOrderId(OrderType.parse(orderType)));
                order.setAssociatedOrderIds(orderIds);
            }

        }
        order.setUid(uid);
        order.setAmount(amount);
        order.setOrderType(orderType);
        order.setPayMethod(payMethod);
        order.setPayChannel(payChannel);
        order.setPayTime(new Date());
        order.setPayType(payType);
        order.setStatus(PayStatus.UNPAY.getValue());
        OrderType orderType1 = OrderType.parse(orderType);
        order.setTitle(orderType1.getOrderTitle());
        order.setBody(orderType1.getOrderBody());
        order.setNeedInvoice(needInvoice);
        order.setExtendParams(extendParams);
        payOrderDao.save(order);
        return order;
    }

    private String getOrderId(OrderType orderType) {
        if (orderType.equals(OrderType.NEEDS_ORDER_PAY)) {
            return BizUtils.getOrderId("ND");
        } else if (orderType.equals(OrderType.PROJECT_SETTLEMENT)) {
            return BizUtils.getOrderId("PD");
        } else if (orderType.equals(OrderType.SOCIAL_WORK_ORDER_PAY)) {
            return BizUtils.getOrderId("WD");
        } else if (orderType.equals(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT)) {
            return BizUtils.getOrderId("WD");
        } else if (orderType.equals(OrderType.RENTER_CHARGE_SETTLEMENT)) {
            return BizUtils.getOrderId("RD");
        }
        return "";
    }

    @Override
    public boolean existOrderId(String orderId) {
        if (payOrderDao.getCountByOrderId(orderId) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int updateInvoiceOrderId(PayOrder payOrder) {

        return payOrderDao.updateInvoiceOrderId(payOrder);
    }

}
