package com.edianniu.pscp.cms.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelResult;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;

/**
 * 定时任务 1.1）未支付工单，超过截止时间的工单自动取消。短信/push提醒；
 * 1.2）已经支付工单，超过截止时间但是没有响应的工单自动取消,并且需要发起【解冻余额/获取提现的时候操作】。短信/push提醒；
 * 1.3）已经支付工单，超过截止时间但是有响应没有一个审核的，自动取消【有响应的工单改为不符合，解冻余额/获取提现的时候操作】。短信/push提醒；
 * 1.4）已经支付工单，超过截止时间但是有响应，大于等于1个确认符合的，工单状态变为结束，其他未确认的默认不符合。短信/push提醒；
 * 1.5）未支付工单，截止时间的工单未到之前短信或者push提醒服务商去支付；
 * 1.6）已经支付工单，快到截止时间1个小时，2个小时，5个小时短信/push提醒一次；
 * 1.7）已完成工单，扫描所有社会工单是否都已结算，如果都已结算，修改工单的状态为已结算；
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月6日 下午7:20:37
 */
@Component("socialWorkOrderTask")
public class SocialWorkOrderTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SocialWorkOrderInfoService socialWorkOrderInfoService;
    
    @Autowired
    private WalletInfoService  walletInfoService;
    

    /**
     * a)扫描未支付并且超过截止时间工单 b)工单自动取消。 c)短信/push提醒派单人
     */
    public void scanAfterExpiryTimeAndUnPayOrders() {
        try {
            List<Long> ids = socialWorkOrderInfoService
                    .getAfterExpiryTimeAndUnPayOrders();
            for (Long id : ids) {
                // 取消订单
                CancelResult result = socialWorkOrderInfoService.cancel(id);
                if (!result.isSuccess()) {
                    logger.error("取消订单[{}]:{}", id, JSON.toJSONString(result));
                }
            }
        } catch (Exception e) {
            logger.error("取消订单:{}", e);
        }

    }

    /**
     * a)扫描未支付并且未到截止时间工单提前xx小时提醒服务商支付 b)短信或者push提醒服务商去支付；
     */
    public void scanBeforeExpiryTimeAndUnPayOrders(String hour) {
        try {
            int h = Integer.parseInt(hour);
            List<Long> ids = socialWorkOrderInfoService
                    .getBeforeExpiryTimeAndUnPayOrders(h);
            for (Long id : ids) {
                // 短信和push提醒
                // 还车key=reminder#id#hour 提醒过的，不要再提醒了
                // dubbo接口根据id+hour 查询当前订单还有多少时间就截止了，提醒用户去支付;
                Result result = socialWorkOrderInfoService
                        .handleBeforeExpiryTimeAndUnPayOrder(id, h);
                if (!result.isSuccess()) {
                    logger.error("未支付订单提醒[{},{}]:{}", id, h, JSON.toJSONString(result));
                }
            }
        } catch (Exception e) {
            logger.error("处理未支付订单提醒:{}", e);
        }

    }

    /**
     * a)扫描已支付并且超过截止时间的订单 b)没有响应的工单自动取消,并且需要发起【解冻余额/获取提现的时候操作】
     * c)有响应没有一个审核的，自动取消【有响应的工单改为不符合，解冻余额/获取提现的时候操作】
     * d)有响应，大于等于1个确认符合的，工单状态变为结束，其他未确认的默认不符合。 e)
     * @param hour
     */
    public void scanAfterExpiryTimeAndPaiedOrders() {
        try {
            List<Long> ids = socialWorkOrderInfoService
                    .getAfterExpiryTimeAndPaiedOrders();
            for (Long id : ids) {
                Result result = socialWorkOrderInfoService
                        .handleAfterExpiryTimeAndPaiedOrder(id);
                if (!result.isSuccess()) {
                    logger.error("处理已支付并且超过截止时间的订单[{},{}]:{}", id);
                }
            }
        } catch (Exception e) {
            logger.error("处理已支付并且超过截止时间的订单:{}", e);
        }
    }

    /**
     * a)扫描已完成工单 b)所有社会工单是否都已结算，如果都已结算，修改工单的状态为已结算 c)是否有多余金额 有的需要解冻余额操作
     */
    public void scanFinishOrders() {
        List<Long> ids = socialWorkOrderInfoService.getFinishOrders();
        for (Long id : ids) {
            Result result = socialWorkOrderInfoService.handleFinishOrder(id);
            if (!result.isSuccess()) {
                logger.error("处理已完成工单[{},{}]:{}", id);
            }
        }
        try {

        } catch (Exception e) {
            logger.error("处理已完成工单:{}", e);
        }
    }
    
    /**
     * 扫描所有已取消、已结算的社会工单（已支付且未退款），并退款
     * @param limit
     */
    public void scanNeedRefundOrders(String limit){
    	try {
			int t =Integer.valueOf(limit);
			List<String> orderIdlist = socialWorkOrderInfoService.scanNeedRefundOrders(t);
    		for (String orderId : orderIdlist) {
				com.edianniu.pscp.mis.bean.Result result = walletInfoService.refundSocialWorkOrderDeposit(orderId);
    			if (! result.isSuccess()) {
    				logger.error("处理退款工单失败[{}]:{}", orderId, JSON.toJSON(result));
				}
			}
		} catch (Exception e) {
			logger.error("处理退款工单异常:{}", e);
		}
    }
}
