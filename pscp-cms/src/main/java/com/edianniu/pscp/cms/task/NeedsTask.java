package com.edianniu.pscp.cms.task;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

/**
 * @author zhoujianjian
 * @date 2017年10月16日 下午4:40:29
 */
@Component("needsTask")
public class NeedsTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NeedsInfoService needsInfoService;

    @Autowired
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;

    @Autowired
    private WalletInfoService walletInfoService;

    /**
     * 扫描需求是否超时，将超时需求更改状态
     */
    public void scanOvertimeNeeds(String limit) {
        try {
        	int m = Integer.parseInt(limit);
            List<Long> ids = needsInfoService.getOvertimeNeeds(m);
            for (Long id : ids) {
                Result result = needsInfoService.handleOvertimeNeeds(id);
                if (!result.isSuccess()) {
                    logger.error("处理已超时需求失败[{}]:{}", id, JSON.toJSON(result));
                }
            }
        } catch (Exception e) {
            logger.error("处理已超时需求异常:{}", e);
        }
    }

    /**
     * 扫描并处理已响应但超时支付保证金的需求响应订单
     */
    public void scanOvertimeAndUnPayNeedsorders(String minutes) {
        try {
            int m = Integer.parseInt(minutes);
            List<Long> ids = customerNeedsOrderInfoService.getOvertimeAndUnPayNeedsorders(m);
            for (Long id : ids) {
                Result result = customerNeedsOrderInfoService.handleOvertimeAndUnPayNeedsorders(id);
                if (!result.isSuccess()) {
                    logger.error("处理超时支付保证金的需求响应订单失败[{}, {}]:{}", id, minutes, JSON.toJSON(result));
                }
            }
        } catch (Exception e) {
            logger.error("处理超时支付保证金的需求响应订单异常:{}", e);
        }
    }

    /**
     * 扫描所有取消、不符合、不合作的需求订单（已支付且未退款），并退款
     */
    public void scanNotGoWellNeedsorders(String limit) {
        try {
            int m = Integer.parseInt(limit);
            List<String> needsOrderIdList = customerNeedsOrderInfoService.getNotGoWellNeedsorders(m);
            for (String needsOrderId : needsOrderIdList) {
                com.edianniu.pscp.mis.bean.Result result = walletInfoService.refundNeedsOrderDeposit(needsOrderId);
                if (!result.isSuccess()) {
                    logger.error("退款失败[{}, {}]:{}", needsOrderId, limit, JSON.toJSON(result));
                }
            }
        } catch (Exception e) {
            logger.error("退款异常:{}", e);
        }
    }
}
