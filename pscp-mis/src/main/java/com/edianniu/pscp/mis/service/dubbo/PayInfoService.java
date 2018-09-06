/**
 *
 */
package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.*;

/**
 * 支付接口
 *
 * @author cyl
 */
public interface PayInfoService {
	/**
	 * 查询退款申请记录
	 * @param queryRefundsReq
	 * @return
	 */
	public QueryRefundsResult queryRefunds(QueryRefundsReq queryRefundsReq);
	/**
	 * 重新发起退款申请
	 * @param id 退款数字ID
	 * @param uid 用户ID -1时表示定时任务
	 * @return
	 */
	public Result retryRefund(Long id,Long uid);
	/**
	 * 更新退款状态(根据第三方的查询状态进行更新)
	 * @param id 退款数字ID
	 * @param uid 用户ID uid -1时表示定时任务
	 * @return
	 */
	public Result checkRefundStatus(Long id,Long uid);
	
    /**
     * 支付宝支付结果异步通知
     *
     * @param req
     * @return
     */
    public AlipayNotifyResult alipayNotify(AlipayNotifyReq req);

    /**
     * 微信支付结果异步通知
     *
     * @param req
     * @return
     */
    public WxpayNotifyResult wxpayNotify(WxpayNotifyReq req);
    /**
     * 发起支付
     * 1）调用preparePay支付之前调用
     * 2）根据业务编号获取支付金额
     * 3）获取支付方式列表，并且判断是否可以使用余额支付进行支付
     * 4）获取会员钱包帐号余额
     * @param startPayReq
     * @return
     */
    public StartPayResult startPay(StartPayReq startPayReq);
    /**
     * 予支付接口
     * 1）调用支付之前先下单
     * 2）根据支付类型和支付方式的不一样，返回不同的数据
     *  2.1）支付宝
     *  2.1.1）pc网站支付 ->下发支付页面->跳转到支付宝网页
     *  2.1.2）app支付->下发调起支付app的信息
     *  2.1.3）手机网站支付->下发支付页面->跳转到支付宝H5页面(检查有没有支付宝APP，有唤醒支付宝app)
     *  2.2)微信支付
     *  2.2.1）扫码支付->下发支付页面并生成二维码
     *  2.2.2）app支付->调用微信统一下单接口，下发调起支付app的信息
     *  2.3）余额支付
     *  2.3.1) 网页支付
     *  2.3.2）app支付
     * @param preparePayReq
     * @return
     */
    public PreparePayResult preparePay(PreparePayReq preparePayReq);

    /**
     * 确认支付接口
     * 1）支付成功同步通知
     * @param confirmPayReq
     * @return
     */
    public ConfirmPayResult confirmPay(ConfirmPayReq confirmPayReq);
    /**
     * 根据UID和订单ID查询支付订单信息
     * @param uid
     * @param orderId
     * @return
     */
    public QueryPayOrderResult queryPayOrder(Long uid,String orderId);
    
    /**
     * 银联代付接口.
     * @param unionpaydfPayReqData
     * @return
     */
    public UnionpaydfPayRespData unionpayDfPay(UnionpaydfPayReqData unionpaydfPayReqData);
    
    /**
     * 银联代付异步通知
     * @param unionpayNotifyReq
     * @return
     * @throws Exception 
     */
    public UnionpayNotifyResult unionpaydfNotify(UnionpayNotifyReq unionpayNotifyReq) throws Exception;
    
    /**
    *
    * 通知
    * @param orderId 要查询的退款订单编号 (
    * @return
    * @throws Exception 
    */ 
   public UnionpayNotifyResult unionpayNotify(UnionpayNotifyReq unionpayNotifyReq)throws Exception;
   
   /**
    * 查詢支付訂單列表
    * @param uid  
    * @param type  支付類型
    * @param offset 
    * @param status支付狀態
    * @return
    */
   public PayOrderListResult queryPayOrderList(Long uid, Integer type, Integer status, Integer offset);
}
