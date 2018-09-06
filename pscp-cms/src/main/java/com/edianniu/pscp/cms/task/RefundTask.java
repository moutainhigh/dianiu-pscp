package com.edianniu.pscp.cms.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.MemberRefundInfo;
import com.edianniu.pscp.mis.bean.pay.MemberRefundStatus;
import com.edianniu.pscp.mis.bean.pay.QueryRefundsReq;
import com.edianniu.pscp.mis.bean.pay.QueryRefundsResult;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;

/*
 * 退款任务
 *
 */
@Component("refundTask")
public class RefundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PayInfoService payInfoService;

    /**
     * 检查退款状态
     */
    public void checkRefundStatus(String limit) {
        try {
        	QueryRefundsReq queryRefundsReq=new QueryRefundsReq();
        	queryRefundsReq.setLimit(Integer.parseInt(limit));
        	queryRefundsReq.setStatus(MemberRefundStatus.PROCESSING.getValue());
        	QueryRefundsResult result=payInfoService.queryRefunds(queryRefundsReq);
        	if(result.isSuccess()){
        		for(MemberRefundInfo refundInfo:result.getRefunds()){
        			Result crsResult=payInfoService.checkRefundStatus(refundInfo.getId(), -1L);
        			if(!crsResult.isSuccess()){
        				logger.error("checkRefundStatus-checkRefundStatus:{}", JSON.toJSON(crsResult));
        			}
        		}
        	}
        	else{
        		logger.error("checkRefundStatus-queryRefunds:{}", JSON.toJSON(result));
        	}
        	
        } catch (Exception e) {
            logger.error("checkRefundStatus:{}", e);
        }
    }

    /**
     * 重新提交退款申请
     */
    public void retryRefund(String limit) {
        try {
        	QueryRefundsReq queryRefundsReq=new QueryRefundsReq();
        	queryRefundsReq.setLimit(Integer.parseInt(limit));
        	queryRefundsReq.setStatus(MemberRefundStatus.FAIL.getValue());
        	QueryRefundsResult result=payInfoService.queryRefunds(queryRefundsReq);
        	if(result.isSuccess()){
        		for(MemberRefundInfo refundInfo:result.getRefunds()){
        			Result crsResult=payInfoService.retryRefund(refundInfo.getId(), -1L);
        			if(!crsResult.isSuccess()){
        				logger.error("retryRefund-retryRefund:{}", JSON.toJSON(crsResult));
        			}
        		}
        	}
        	else{
        		logger.error("retryRefund-queryRefunds:{}", JSON.toJSON(result));
        	}
           
        } catch (Exception e) {
            logger.error("retryRefund:{}", e);
        }
    }   
}
