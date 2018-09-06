/**
 *
 */
package com.edianniu.pscp.mis.service;

import java.util.List;

import com.edianniu.pscp.mis.bean.pay.AliPayPush;
import com.edianniu.pscp.mis.bean.pay.PayConfirmResult;
import com.edianniu.pscp.mis.bean.pay.PayList;
import com.edianniu.pscp.mis.bean.pay.PayNotifyResult;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.WxPayPush;
import com.edianniu.pscp.mis.domain.MemberRefund;
import com.edianniu.pscp.mis.domain.PayConfirm;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.PayType;
import com.edianniu.pscp.mis.domain.UnionpayPushLog;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.domain.WxPushLog;


/**
 * @author cyl
 */
public interface PayService {
	
	public List<PayType> getPayTypes();
    /**
     * 支付通知
     *
     * @param order    通知的订单
     * @param pushInfo 推送的通知内容
     */
    public PayNotifyResult asyncNotify(PayOrder order, PayPush pushInfo) throws Exception;
    /**
     * 重复支付退款
     * @param order
     * @param pushInfo
     * @param refundAmount
     * @throws Exception
     */
    public void refund(PayOrder order, PayPush pushInfo,Double refundAmount) throws Exception;
    /**
     * 重复支付退款
     * @param memberRefund
     * @throws Exception
     */
    public void refund(MemberRefund memberRefund) throws Exception;
    /**
     * 检查退款状态
     * @param memberRefund
     * @throws Exception
     */
    public void checkRefundStatus(MemberRefund memberRefund) throws Exception;
    
    
    /**
     * 支付通知(提现订单)
     *
     * @param order    通知的订单
     * @param pushInfo 推送的通知内容
     */
    public void unionPaydfNotify(UserWalletDetail order,PayPush pushInfo)throws Exception;

    /**
     * 记录接口推送内容日志(微信)
     *
     * @param pushInfo 推送的信息
     */
    void wxPushLog(WxPayPush pushInfo);

    /**
     * 记录接口推送内容日志(支付宝)
     *
     * @param pushInfo 推送的信息
     */
    void aliPushLog(AliPayPush pushInfo);

    /**
     * 记录微信推送处理异常的信息
     *
     * @param reqXml 微信推送的xml
     * @param errMsg 错误的异常信息
     */
    void wxPushError(String reqXml, String errMsg);

    /**
     * 记录支付宝推送处理异常信息
     *
     * @param pushJson
     * @param errMsg
     */
    void aliushError(String pushJson, String errMsg);

    /**
     * 重复支付退款.
     *
     * @param order
     * @param pushInfo
     * @param userInfo
     * @throws Exception
     */
    //void repeatedRefund(UserWalleRechargeOrder order, PayPush pushInfo, UserInfo userInfo) throws Exception;
    
    
    public List<PayList> getPayList();
    
    public List<PayList> getPayListDelWalletPay();
    /**
     * 银联手机控件支付日志
     *
     * @param unionpayPushLog
     * 
     */
    public void unionpayPushLog(UnionpayPushLog unionpayPushLog);
    
    /**
     * 银联代付日志
     *
     * @param unionpayPushLog
     * 
     */
    public void unionpaydfPushLog(UnionpayPushLog unionpaydfPushLog);
    
    /**
     * 获取银联支付日志信息
     *
     * @param unionpayPushLog
     * 
     */
    public UnionpayPushLog getUnionpayPushLog(Long id);
    
    /**
     * 修改银联支付日志状态信息
     *
     * @param unionpayPushLog
     * 
     */
    public boolean updateUnionPushLog(UnionpayPushLog unionpayPushLog);
    
    /**
     * 条件查询列表
     *
     * @param unionpayPushLog
     * 
     */
    public List<UnionpayPushLog> getUnionPushLogs(UnionpayPushLog unionpayPushLog)throws Exception;
    
    
    
    /**
     * 条件查询列表
     *
     * @param unionpayPushLog
     * 
     */
    public List<UnionpayPushLog> getUnionPushLogByOrderIds(List<String >orderIds);
    
    /**
     * 条件查询列表
     *
     * @param unionpayPushLog
     * 
     */
    public UnionpayPushLog getUnionPushLogByOrderId(String orderId);
    /**
     * 查询wx支付pushlog
     * @param orderId
     * @return
     */
    public WxPushLog getWxPushLogByOrderId(String orderId);
    
    /**
     * 第三方支付确认
     * 余额
     * 支付宝
     * 微信
     * 银联支付
     * 其他
     * @param orderId
     * @param payType
     * @param payStatus
     * @param payMemo
     * @return
     */
    public PayConfirmResult paymentConfirm(PayConfirm payConfirm);
    

}
