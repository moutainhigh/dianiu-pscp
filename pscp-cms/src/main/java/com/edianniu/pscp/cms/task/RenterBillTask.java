package com.edianniu.pscp.cms.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterOrderInfoService;
/**
 * 租客账单任务
 * 1）月付费账单生成任务
 * 2) 预付费账单生成任务
 * 3）预付费账单扣款任务
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月16日 下午2:27:55 
 * @version V1.0
 */
@Component("renterBillTask")
public class RenterBillTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RenterInfoService renterInfoService;

    @Autowired
    private RenterOrderInfoService renterOrderInfoService;

    @Autowired
    private WalletInfoService walletInfoService;

    /**
     * 扫描月结算的租客列表，并且生成月账单
     */
    public void scanMonthlyPayment(String limit) {
        try {
        	int m = Integer.parseInt(limit);
        	List<Long> renterIds=renterInfoService.getMonthlyPaymentRenterIds(m);
        	for(Long renterId:renterIds){
        		Result beforeResult=renterOrderInfoService.beforeHandleOrder(renterId);
        		if(beforeResult.isSuccess()){
        			Result result=renterOrderInfoService.handleMonthlyPaymentOrder(renterId);
        			if(!result.isSuccess()){
        				logger.error("生成月结算电费账单失败[{}, {}]:{}", renterId, limit, JSON.toJSON(result));
        				renterOrderInfoService.afterHandlerOrder(renterId);
        			}
        		}
        	}
        } catch (Exception e) {
            logger.error("生成月结算电费账单异常:{}", e);
        }
    }

    /**
     * 扫描预付费的租客列表，并且生成|更新月账单
     */
    public void scanPrePayment(String limit) {
        try {
            int m = Integer.parseInt(limit);
            List<Long> renterIds=renterInfoService.getPrePaymentRenterIds(m);
        	for(Long renterId:renterIds){
        		//首先对任务状态加锁
        		Result beforeResult=renterOrderInfoService.beforeHandleOrder(renterId);
        		if(beforeResult.isSuccess()){
        			Result result=renterOrderInfoService.handlePrePaymentOrder(renterId);
        			if(!result.isSuccess()){
        				logger.error("生成预付费电费账单失败[{}, {}]:{}", renterId, limit, JSON.toJSON(result));
        				renterOrderInfoService.afterHandlerOrder(renterId);
        			}
        		}
        		
        	}
        } catch (Exception e) {
            logger.error("生成预付费电费账单异常:{}", e);
        }
    }
    /**
     * 扫描需要扣款的预付费账单,调用余额进行扣款并且账单更新状态
     * 调用renter-mis接口 获取预付费账单列表
     * 调用renter-mis接口 进行扣款，renter-mis接口调用pscp-mis接口进行扣款操作
     * @param limit
     */
    public void scanPrePaymentUnsettledOrders(String limit){
    	try {
        	int m = Integer.parseInt(limit);
        	List<String> orderIds=renterOrderInfoService.getPrePaymentUnsettledOrders(m);
        	for(String orderId:orderIds){
        		com.edianniu.pscp.mis.bean.Result result=walletInfoService.settlementPrepayRenterChargeOrder(orderId);
        		if(!result.isSuccess()){
        			logger.error("预付费账单结算失败[{}, {}]:{}", orderId, limit, JSON.toJSON(result));
        		}
        	}
        } catch (Exception e) {
            logger.error("预付费账单结算异常:{}", e);
        }
    }
}
