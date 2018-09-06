package com.edianniu.pscp.mis.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.dao.PayLogDao;
import com.edianniu.pscp.mis.dao.PayTypeDao;
import com.edianniu.pscp.mis.dao.ThirdPayListDao;
import com.edianniu.pscp.mis.dao.UserWalletDetailDao;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.service.pay.AliPayService;
import com.edianniu.pscp.mis.service.pay.WxPayService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import com.edianniu.pscp.mis.util.MoneyUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Service
@Repository("payService")
public class DefaultPayService implements PayService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultPayService.class);

	private final int MODIFY_MAX_TRY = 2;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private UserWalletDetailDao userWalletDetailDao;
	@Autowired
	private SocialWorkOrderService socialWorkOrderService;
	@Autowired
	private ElectricianWorkOrderService electricianWorkOrderService;

	@Autowired
	private PayLogDao payLogDao;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private WxPayService wxPayService;
	@Autowired
	private AliPayService aliPayService;
	@Autowired
	private PayTypeDao payTypeDao;
	@Autowired
	private MemberRefundService memberRefundService;
	@Autowired
    @Qualifier("cachedService")
    private CachedService cachedService;

	@Autowired
	private ThirdPayListDao thirdPayListDao;



	/**
	 * 订单支付通知
	 *
	 * @param order
	 *            通知的订单
	 * @param pushInfo
	 *            推送的通知内容
	 */
	@Transactional
	@Override
	public PayNotifyResult asyncNotify(PayOrder order, PayPush pushInfo) throws Exception {
		PayNotifyResult result=new PayNotifyResult();
		// 获取交易状态
		/*// TODO 根据交易状态, 执行不同业务
		switch (tradeStatus) {
			case WAIT_BUYER_PAY:
				// 支付宝下单成功, 不做任何处理
				return result;
			case TRADE_CLOSED:
				// 支付宝交易关闭
				// 微信交易关闭.
				// 暂不处理
				return result;
			case TRADE_SUCCESS:
			case TRADE_FINISHED:
				// 支付宝交易成功, 可退款.
				// 微信支付成功
				// 继续
				break;
		}*/

		Long uid = order.getUid();
		// 获取钱包信息
		UserWallet wall = this.userWalletService.getByUid(uid);
		if (wall == null) {
			throw new BusinessException(String.format("用户 [%s] 钱包信息不存在!", uid));
		}
		/*
		 * 更改订单状态
		 */
		// 支付时间
		Date payTime ;
		if(pushInfo.getPayType().intValue()==PayType.UNIONPAY.getValue()){
			payTime = DateUtil.formString(pushInfo.getTxnTime(), "yyyyMMddHHmmss");
		}else{
			payTime = DateUtil.formString(pushInfo.getTimeEnd(), "yyyy-MM-dd HH:mm:ss");
		}

		// 更新支付订单状态 当前重试次数
		// 更新支付订单状态重试次数
		int tryIndex = 0;
		// 更新是否成功标识
		boolean isUpdated;
		// 执行修改, 取返回结果
		// 修改失败时, 重试tryCount次
		while (!(isUpdated = updatePayOrderStatus(order.getOrderId(), payTime, pushInfo.getPayType()))
				&& tryIndex < MODIFY_MAX_TRY) {
			logger.error(String.format("更新支付订单支付状态失败![%s]已尝试%s次", order.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("准备更新支付订单支付状态![%s]第%s次尝试", order.getOrderId(), tryIndex));
			try {
				// 两个进程同时操作时,并且产生脏数据时，
				// 线程停止100ms，继续执行一次
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
		// 判断修改状态.
		if(!isUpdated){
			// 若尝试了tryCount次 修改没成功.
			// 终止本次处理.
			throw new BusinessException(String.format("支付订单[%s]更新支付状态失败!共尝试%s次", order.getOrderId(), MODIFY_MAX_TRY));
		}
		isUpdated=this.orderPayService.updateOrderStatus(order);
		// 判断修改状态.
		if (!isUpdated) {
			// 若尝试了tryCount次 修改没成功.
			// 终止本次处理.
			throw new BusinessException(String.format("社会工单更新失败!订单[%s]共尝试%s次", order.getOrderId(), MODIFY_MAX_TRY));
		}
		/*
		 * 钱包金额
		 */
		// 初始化更新状态 复用.
		tryIndex = 0;
		while (!(isUpdated = orderPayService.updateUserWallet(order))
				&& tryIndex < MODIFY_MAX_TRY) {
			logger.error(String.format("更新钱包金额![%s]已尝试%s次", order.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("准备更新钱包金额![%s]第%s次尝试", order.getOrderId(), tryIndex));
			try {
				// 两个进程同时操作时,并且产生脏数据时，
				// 线程停止100ms，继续执行一次
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
		// 判断修改状态.
		if (!isUpdated) {
			// 若尝试了tryCount次 修改没成功.
			// 终止本次处理.
			throw new BusinessException(String.format("钱包金额更新失败!订单[%s]共尝试%s次", order.getOrderId(), MODIFY_MAX_TRY));
		}
		order.setTppTransactionId(pushInfo.getTransactionId());
		order.setTppSellerId(pushInfo.getSellerId());
		order.setTppBuyerId(pushInfo.getBuyerId());
		orderPayService.addUserWalletDetail(order);
		return result;

	}
	/**
	 * 更新订单支付状态
	 *
	 * @param orderId 订单编号
	 * @param payTime 支付时间
	 * @param payType 支付类型
	 * @return
	 */
	private boolean updatePayOrderStatus(String orderId, Date payTime, Integer payType) throws BusinessException {
		PayOrder payOrder=payOrderService.getPayOrderByOrderId(orderId);
		if(payOrder==null){
			throw new BusinessException("order:{"+orderId+"} is null");
		}
		// 支付类型
		payOrder.setPayType(payType);
		// 支付状态为 已支付
		payOrder.setStatus(PayStatus.SUCCESS.getValue());
		// 支付时间
		payOrder.setPayAsyncTime(payTime);
		payOrder.setModifiedUser("push");
		int result=payOrderService.updatePayStatus(payOrder);
		if(result>0){
			if(payOrder.getNeedInvoice()!=null&&payOrder.getNeedInvoice()==1){//需要发票，生成发票
				//TODO 
				//获取发票抬头
				//生成发票信息
			}
			return true;
		}
		return false;
	}

	/**
	 * 记录接口推送内容日志(支付宝)
	 *
	 * @param pushInfo 推送的信息
	 */
	@Override
	public void aliPushLog(AliPayPush pushInfo) {
		try {
			AliPushLog pushLog = new AliPushLog();
			BeanUtils.copyProperties(pushLog, pushInfo);
			pushLog.setTradeNo(pushInfo.getTransactionId());
			this.payLogDao.saveAliLog(pushLog);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("记录微信推送日志失败!", e);
		}
	}

	/**
	 * 记录接口推送内容日志(微信)
	 *
	 * @param pushInfo 推送的信息
	 */
	@Override
	public void wxPushLog(WxPayPush pushInfo) {
		try {
			WxPushLog pushLog = new WxPushLog();
			BeanUtils.copyProperties(pushLog, pushInfo);
			this.payLogDao.saveWxLog(pushLog);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("记录微信推送日志失败!", e);
		}
	}

	private boolean updateSyncPayOrder(PayConfirm payConfirm,PayConfirmResult payConfirmResult){
		boolean result=false;
		PayOrder payOrder = payOrderService.getPayOrder(payConfirm.getUid(), payConfirm.getOrderId());
		if(payOrder==null){
			return result;
		}
		if(payConfirm.getPayType().equals(PayType.WALLET)){//余额支付
			if(payOrder.getStatus()==PayStatus.UNPAY.getValue()){
				payOrder.setPayType(payConfirm.getPayType().getValue());
				payOrder.setStatus(payConfirm.getPayStatus().getValue());
				payOrder.setMemo(payConfirm.getPayMemo());
				payOrder.setModifiedUser("系统");
				Date time=new Date();
				payOrder.setPaySyncTime(time);
				payOrder.setPayAsyncTime(time);
				int row=payOrderService.updatePayStatus(payOrder);
				if(row==1){
					orderPayService.updateOrderStatus(payOrder);
					payOrder.setTppTransactionId(payOrder.getOrderId());
					payOrder.setTppBuyerId("");
					payOrder.setTppSellerId("");
					orderPayService.updateUserWallet(payOrder);
					orderPayService.addUserWalletDetail(payOrder);
					payConfirmResult.setAsync(true);//异步通知成功
					result=true;
				}

			}
			else{
				//重复操作
				payConfirmResult.setAsync(true);//异步通知成功
				payConfirmResult.setRepeat(true);
				result=true;
			}
		}
		else{//其他第三方支付
			if(payOrder.getStatus()==PayStatus.UNPAY.getValue()||
					payOrder.getStatus()==PayStatus.FAIL.getValue()||
							payOrder.getStatus()==PayStatus.CANCLE.getValue()){
				payOrder.setPayType(payConfirm.getPayType().getValue());
				payOrder.setStatus(payConfirm.getPayStatus().getValue());
				payOrder.setMemo(payConfirm.getPayMemo());
				payOrder.setPaySyncTime(new Date());
				payOrder.setModifiedUser("系统");
				int rowNum=payOrderService.updatePayStatus(payOrder);
				logger.info("confirm  其他第三方支付 order:"+rowNum);
				if(rowNum==1){
					orderPayService.updateSyncOrderStatus(payOrder);
					return true;
				}
			}
			else if(payOrder.getStatus()==PayStatus.SUCCESS.getValue()){
				payConfirmResult.setAsync(true);//异步通知成功
				if(payOrder.getPayType()!=payConfirm.getPayType().getValue()){
					payConfirmResult.setRepeat(true);
				}
			}
			else{
				throw new RuntimeException("系统未知错误!");
			}
		}

		return result;
	}







	/**
	 * 记录推送处理异常的信息
	 *
	 * @param reqXml 微信推送的xml
	 * @param errMsg 错误的异常信息
	 */
	@Override
	public void wxPushError(String reqXml, String errMsg) {
		try {
			PayPushError error = new PayPushError();
			error.setPush(reqXml);
			error.setErrMsg(errMsg);
			error.setPayType(2);
			error.setCreateUser("系统");
			this.payLogDao.insertPayPushError(error);
		} catch (Exception e) {
			logger.error("wxPushError {}", e);
		}
	}

	/**
	 * 记录支付宝推送处理异常信息
	 *
	 * @param pushJson
	 * @param errMsg
	 */
	@Override
	public void aliushError(String pushJson, String errMsg) {
		try {
			PayPushError error = new PayPushError();
			error.setPush(pushJson);
			error.setErrMsg(errMsg);
			error.setPayType(3);
			error.setCreateUser("系统");
			this.payLogDao.insertPayPushError(error);
		} catch (Exception e) {
			logger.error("aliushError {}", e);
		}
	}
	@Override
	public List<PayList> getPayList() {
		List<PayList> payList = thirdPayListDao.getPayList();
		return payList;
	}
	@Override
	public void unionpayPushLog(UnionpayPushLog unionpayPushLog) {

			payLogDao.saveUnionLog(unionpayPushLog);



	}
	@Override
	public void unionpaydfPushLog(UnionpayPushLog unionpaydfPushLog) {

			payLogDao.saveUniondfLog(unionpaydfPushLog);

	}
	@Override
	public UnionpayPushLog getUnionpayPushLog(Long id) {
		UnionpayPushLog log= payLogDao.getUnionpayPushLog(id);
		return log;
	}
	@Override
	public boolean updateUnionPushLog(UnionpayPushLog unionpayPushLog) {
		boolean flag=false;
		try{
			payLogDao.updateUnionpayPushLog(unionpayPushLog);
			flag=true;
		}catch(Exception e){
			logger.info("updateUnionPushLog", e);
			flag=false;
		}

		return flag;

	}
	@Override
	public List<UnionpayPushLog> getUnionPushLogs(UnionpayPushLog unionpayPushLog) {
		List<UnionpayPushLog> list=payLogDao.getUnionpayPushLogs(unionpayPushLog);
		return list;
	}
	@Override
	public void unionPaydfNotify(UserWalletDetail order, PayPush pushInfo) throws Exception {

		// 获取交易状态
		//TradeStatus tradeStatus = this.getTradeStatus(pushInfo);

		// TODO 根据交易状态, 执行不同业务
		/*switch (tradeStatus) {
			case WAIT_BUYER_PAY:
				// 支付宝下单成功, 不做任何处理
				return ;
//				break;
			case TRADE_CLOSED:
				// 支付宝交易关闭
				// 微信交易关闭.
				// 暂不处理
				return ;
//				break;
//			case TRADE_SUCCESS:
//				break;
//			case TRADE_FINISHED:
//				break;
			case TRADE_SUCCESS:
			case TRADE_FINISHED:
				// 支付宝交易成功, 可退款.
				// 微信支付成功
				// 继续
				break;
		}*/

		// 支付金额, 单位分
		String totalFee=pushInfo.getTotalFee();
		if(totalFee==null){
					totalFee="0";
		}
		Long uid = order.getUid();
		// 获取钱包信息
		UserWallet wall = this.userWalletService.getByUid(uid);
		if (wall == null) {
			throw new BusinessException(String.format("用户 [%s] 钱包信息不存在!", uid));
		}
		// 支付时间
		Date payTime = DateUtil.formString(pushInfo.getTxnTime(), "yyyyMMddHHmmss");
		// 更新订单状态重试次
		int tryIndex = 0;
		// 更新是否成功标识
		boolean isUpdated;
		while(!(isUpdated=updateUserwalleDetailStatus(order,payTime))&& tryIndex < MODIFY_MAX_TRY){
			logger.error(String.format("更新租车订单支付状态失败![%s]已尝试%s次", order.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("准备更新租车订单支付状态![%s]第%s次尝试", order.getOrderId(), tryIndex));
			try {
				// 两个进程同时操作时,并且产生脏数据时，
				// 线程停止100ms，继续执行一次
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
		/*
		 * 钱包金额
		 */
		// 初始化更新状态 复用.
	/*	tryIndex = 0;
		while (!(isUpdated = this.updateWallAmount(null, 0D))
				&& tryIndex < MODIFY_MAX_TRY) {
			logger.error(String.format("更新钱包金额![%s]已尝试%s次", order.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("准备更新钱包金额![%s]第%s次尝试", order.getOrderId(), tryIndex));
			try {
				// 两个进程同时操作时,并且产生脏数据时，
				// 线程停止100ms，继续执行一次
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}*/
		// 判断修改状态.
		if (!isUpdated) {
			// 若尝试了tryCount次 修改没成功.
			// 终止本次处理.
			throw new BusinessException(String.format("提现订单[%s]更新提现状态失败!共尝试%s次", order.getOrderId(), MODIFY_MAX_TRY));
		}
	}

	private boolean updateUserwalleDetailStatus(UserWalletDetail order, Date payTime){
		boolean flag=false;
		/*
		order.setStatus(WalleDetailPayStatus.PAY_SUCCESS.getValue());
		order.setPayTime(payTime);
		order.setPayUser("系统");
		order.setConfirmTime(new Date());
		userWalleDetailDao.payConfirm(order);
		*/
		flag=true;
		return flag;

	}
	@Override
	public List<PayList> getPayListDelWalletPay() {

		return thirdPayListDao.getPayListDelWalletPay();
	}
	@Override
	public List<UnionpayPushLog> getUnionPushLogByOrderIds(List<String> orderIds) {
		//因为银联通知会发送多条通知，可能状态不同或相同，所以同一条orderid可能有多条记录,所以要去重
		List<UnionpayPushLog> logs=payLogDao.getUnionpayPushLogByOrderIds(orderIds);
		Map<String,UnionpayPushLog>map=new HashMap<String,UnionpayPushLog>();
		if(!logs.isEmpty()&&logs.size()!=0){
			for(UnionpayPushLog log :logs){
				map.put(log.getOrderId(), log);
			}
		}
		List<UnionpayPushLog> newlogs=new ArrayList<UnionpayPushLog>();
		for (Map.Entry<String,UnionpayPushLog> entry : map.entrySet()) {
			newlogs.add(entry.getValue())  ;
		}
		return newlogs;
	}
	@Override
	public UnionpayPushLog getUnionPushLogByOrderId(String orderId) {
		UnionpayPushLog log=payLogDao.getUnionpayPushLogByOrderId(orderId);
		return log;
	}
	@Override
	public WxPushLog getWxPushLogByOrderId(String orderId) {

		return payLogDao.getWxPushLogByOrderId(orderId);
	}
	@Override
	@Transactional
	public PayConfirmResult paymentConfirm(PayConfirm payConfirm){
		PayConfirmResult result=new PayConfirmResult();
		int tryIndex = 0;
		// 更新是否成功标识
		boolean isUpdated;
		// 执行修改, 取返回结果
		// 修改失败时, 重试tryCount次
		while (!(isUpdated = updateSyncPayOrder(payConfirm,result))
				&& tryIndex < MODIFY_MAX_TRY) {
			logger.error(String.format("同步更新支付订单支付状态失败![%s]已尝试%s次", payConfirm.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("同步更新支付订单支付状态![%s]第%s次尝试", payConfirm.getOrderId(), tryIndex));
			try {
				// 两个进程同时操作时,并且产生脏数据时，
				// 线程停止100ms，继续执行一次
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
		return result;
	}
	@Override
	public void refund(MemberRefund memberRefund) throws Exception {
		if(memberRefund==null){
			throw new Exception("memberRefund is null");
		}
		if (memberRefund.getPayType() == PayType.WALLET.getValue()) {
			// 余额支付, 不存在这种情况.
		}
		else if (memberRefund.getPayType() == PayType.WEIXIN.getValue()) {
			//原路返回到微信支付
			WxPayRefundReqData reqData = new WxPayRefundReqData();
			reqData.setAppId(memberRefund.getAppId());
			reqData.setMchId(memberRefund.getMchId());
			reqData.setOutRefundNo(memberRefund.getOutRefundNo());
			reqData.setOutTradeNo(memberRefund.getOutTradeNo());
			reqData.setRefundFee(MoneyUtils.yuanToStrFen(memberRefund.getRefundFee()));
			reqData.setRefundFeeType("CNY");
			reqData.setRefundReason(memberRefund.getRefundReason());
			reqData.setTotalFee(MoneyUtils.yuanToStrFen(memberRefund.getTotalFee()));
			reqData.setTradeNo(memberRefund.getTradeNo());
			reqData.setTradeType(memberRefund.getTradeType());
			WxPayRefundRespData respData = wxPayService.refund(reqData);//提交退款申请
			if (respData.isSuccess()) {
				memberRefund.setRefundStatus(MemberRefundStatus.PROCESSING.getValue());
				memberRefund.setRefundMemo(respData.getErrorMessage());
				memberRefund.setRefundNo(respData.getRefund_id());
			} else {
				memberRefund.setRefundStatus(MemberRefundStatus.FAIL.getValue());
				memberRefund.setRefundMemo(respData.getErrorMessage());
			}
			saveOrUpdateRefund(memberRefund);
			logger.info("退款信息 ：{}",JSON.toJSONString(memberRefund));
		} else if (memberRefund.getPayType() == PayType.ALIPAY.getValue()) {
			AliPayRefundReqData reqData=new AliPayRefundReqData();
			reqData.setAppId(memberRefund.getAppId());
			reqData.setOutRefundNo(memberRefund.getOutRefundNo());
			reqData.setOutTradeNo(memberRefund.getOutTradeNo());
			reqData.setRefundFee(MoneyUtils.format(memberRefund.getRefundFee()));
			reqData.setRefundReason(memberRefund.getRefundReason());
			reqData.setTotalFee(MoneyUtils.format(memberRefund.getTotalFee()));
			reqData.setTradeNo(memberRefund.getTradeNo());
			reqData.setTradeType(memberRefund.getTradeType());
			AliPayRefundRespData respData=aliPayService.refund(reqData);
			if(respData.isSuccess()){
				memberRefund.setRefundStatus(MemberRefundStatus.PROCESSING.getValue());
				memberRefund.setRefundMemo(respData.getResultMessage());
				memberRefund.setRefundTime(respData.getRefundTime());
				memberRefund.setRefundNo(memberRefund.getOutRefundNo());
			}
			else{
				memberRefund.setRefundStatus(MemberRefundStatus.FAIL.getValue());
				memberRefund.setRefundMemo(respData.getResultMessage());
			}
			saveOrUpdateRefund(memberRefund);
			logger.info("退款信息 ：{}",JSON.toJSONString(memberRefund));
		}
		else if(memberRefund.getPayType()==PayType.UNIONPAY.getValue()){

		}
		else{

		}
	}
	@Override
	public void checkRefundStatus(MemberRefund memberRefund) throws Exception {
		if(memberRefund==null){
			throw new Exception("memberRefund is null");
		}
		if (memberRefund.getPayType() == PayType.WALLET.getValue()) {
			// 余额支付, 不存在这种情况.
		}
		else if (memberRefund.getPayType() == PayType.WEIXIN.getValue()) {
			//原路返回到微信支付
			WxPayRefundQueryReqData reqData = new WxPayRefundQueryReqData();
			reqData.setAppId(memberRefund.getAppId());
			reqData.setMchId(memberRefund.getMchId());
			reqData.setOutRefundNo(memberRefund.getOutRefundNo());
			reqData.setOutTradeNo(memberRefund.getOutTradeNo());
			reqData.setTradeNo(memberRefund.getTradeNo());
			reqData.setTradeType(memberRefund.getTradeType());
			reqData.setRefundNo(memberRefund.getRefundNo());
			WxPayRefundQueryRespData respData = wxPayService.refundQuery(reqData);//查询退款记录
			if (respData!=null&&"SUCCESS".equals(respData.getResult_code())&&"SUCCESS".equals(respData.getReturn_code())) {
				memberRefund.setRefundStatus(MemberRefundStatus.SUCCESS.getValue());
				memberRefund.setRefundMemo("退款成功");
			} else {
				if(respData==null){
					memberRefund.setRefundMemo("微信支付订单查询接口调用异常");
				}
				else{
					if("FAIL".equals(respData.getReturn_code())){
						memberRefund.setRefundMemo(respData.getReturn_msg());
					}
					else{
						memberRefund.setRefundMemo("err_code:"+respData.getErr_code()+",err_code_des:"+respData.getErr_code_des());
					}
				}
			}
			memberRefundService.update(memberRefund);
			logger.info("退款信息 ：{}",JSON.toJSONString(memberRefund));
		} else if (memberRefund.getPayType() == PayType.ALIPAY.getValue()) {
			AliPayRefundQueryReqData reqData=new AliPayRefundQueryReqData();
			reqData.setAppId(memberRefund.getAppId());
			reqData.setOutRefundNo(memberRefund.getOutRefundNo());
			reqData.setOutTradeNo(memberRefund.getOutTradeNo());
			reqData.setTradeNo(memberRefund.getTradeNo());
			reqData.setTradeType(memberRefund.getTradeType());
			AliPayRefundQueryRespData respData=aliPayService.refundQuery(reqData);
			if(respData.isSuccess()){
				if(respData.isRefundSuccess()){
					memberRefund.setRefundStatus(MemberRefundStatus.SUCCESS.getValue());
					memberRefund.setRefundMemo("退款成功");
				}
				else{
					memberRefund.setRefundMemo("支付宝未返回退款信息");
				}
			}
			else{
				memberRefund.setRefundMemo(respData.getResultMessage());
			}
			memberRefundService.update(memberRefund);
			logger.info("退款信息 ：{}",JSON.toJSONString(memberRefund));
		}
		else if(memberRefund.getPayType()==PayType.UNIONPAY.getValue()){

		}
		else{

		}

	}
	private void saveOrUpdateRefund(MemberRefund memberRefund){
		// 插入refund
		if (memberRefund.getId() == null) {
			memberRefundService.save(memberRefund);
		} else {
			memberRefund.setRefundCount(memberRefund.getRefundCount() + 1);
			memberRefundService.update(memberRefund);
		}
	}

	@Override
	public void refund(PayOrder order, PayPush pushInfo,Double refundAmount)
			throws Exception {
		    logger.warn(" payService.refund inside:{},payType:{}",JSON.toJSONString(order),pushInfo.getPayType() );
			MemberRefund memberRefund = new MemberRefund();
			memberRefund.setUid(order.getUid());
			memberRefund.setAppId(pushInfo.getAppid());
			memberRefund.setMchId(pushInfo.getMchId());
			memberRefund.setPayType(pushInfo.getPayType());
			memberRefund.setOrderType(order.getOrderType());
			memberRefund.setTotalFee(order.getAmount());
			memberRefund.setRefundFee(refundAmount);
			memberRefund.setOutTradeNo(pushInfo.getOutTradeNo());
			memberRefund.setTradeNo(pushInfo.getTransactionId());
			memberRefund.setTradeType(pushInfo.getTradeType());
			memberRefund.setOutRefundNo(BizUtils.getOrderId("RD"));
			memberRefund.setRefundNo(null);
			memberRefund.setRefundReason("正常退款");
			memberRefund.setRefundStatus(MemberRefundStatus.PROCESSING.getValue());
			memberRefund.setRefundTime(new Date());
			memberRefund.setRefundCount(1);
			refund(memberRefund);

	}

	@Override
	public List<com.edianniu.pscp.mis.domain.PayType> getPayTypes() {

		return payTypeDao.getPayTypes();
	}



}
