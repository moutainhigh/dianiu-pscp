package com.edianniu.pscp.renter.mis.service.dubbo.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.renter.mis.bean.DefaultResult;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.BillTaskStatus;
import com.edianniu.pscp.renter.mis.bean.renter.ChargeModeType;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.domain.PowerPriceConfig;
import com.edianniu.pscp.renter.mis.domain.Renter;
import com.edianniu.pscp.renter.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.renter.mis.domain.RenterConfig;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;
import com.edianniu.pscp.renter.mis.service.RenterMeterService;
import com.edianniu.pscp.renter.mis.service.RenterOrderService;
import com.edianniu.pscp.renter.mis.service.RenterService;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterOrderInfoService;
import com.edianniu.pscp.renter.mis.util.BizUtils;
import com.edianniu.pscp.renter.mis.util.DateUtils;
import com.edianniu.pscp.renter.mis.util.MoneyUtils;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;

@Service
@Repository("renterOrderInfoService")
public class RenterOrderInfoServiceImpl implements RenterOrderInfoService {
	private static final Logger logger = LoggerFactory
			.getLogger(RenterOrderInfoServiceImpl.class);
	@Autowired
	@Qualifier("renterService")
	private RenterService renterService;

	@Autowired
	@Qualifier("renterOrderService")
	private RenterOrderService renterOrderService;

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("renterMeterService")
	private RenterMeterService renterMeterService;
	@Autowired
	@Qualifier("reportSearchDubboService")
	private ReportSearchDubboService reportSearchDubboService;
	@Autowired
	@Qualifier("walletInfoService")
	private WalletInfoService walletInfoService;
	@Autowired
	@Qualifier("messageInfoService")
	private MessageInfoService messageInfoService;

	@Autowired
	@Qualifier("jedisUtil")
	private JedisUtil jedisUtil;

	@Override
	public Result handlePrePaymentOrder(Long renterId) {
		Result result = new DefaultResult();
		try{
			Renter renter = renterService.getById(renterId);
			if (renter == null) {
				result.set(ResultCode.ERROR_201, "租客信息不存在");
				return result;
			}
			// 获取房东的配置信息，计费周期
			PowerPriceConfig powerPriceConfig = renterOrderService
					.getPowerPriceConfig(renter.getCompanyId());
			if (powerPriceConfig == null) {
				result.set(ResultCode.ERROR_201, "企业配置不存在");
				return result;
			}
			RenterConfig renterConfig = renterService.getRenterConfig(renterId);
			if (renterConfig == null) {
				result.set(ResultCode.ERROR_202, "租客配置不存在");
				return result;
			}
			if(renterConfig.getChargeMode()!=ChargeModeType.PAY_FIRST.getValue()){
				result.set(ResultCode.ERROR_202, "只支持预付费模式");
				return result;
			}
			if(renterConfig.getBillTaskStatus()!=BillTaskStatus.EXECUTING.getValue()){
				result.set(ResultCode.ERROR_202, "账单生成task状态有问题！");
				return result;
			}
			if(renterConfig.getNextBillCreateTime().getTime()>=new Date().getTime()){
				result.set(ResultCode.ERROR_202, "未到账单生成时间，无法生成！");
				return result;
			}
			Integer updateFrequency=60;//账单更新频率，单位分，默认1个小时
			boolean  isBillCycleEnd=false;//true 表示账单周期截止，false表示未截止
			boolean  isRenterExpires=false;//true 表示租客到期,false 表示未到期
			Integer chargeTimeInterval= Integer.valueOf(powerPriceConfig.getChargeTimeInterval());
			Date startTime=renterConfig.getStartTime();
			Date endTime=renterConfig.getEndTime();
			Date nextBillTime=renterConfig.getNextBillCreateTime();
			//下一次生成账单日期 20180401 00:00:00   20180404 23:23 20180405 00:00:00 将再次执行账单20180305-20180404的账单一次并且修改status=1
			//下一个周期开始才跑 20180405~20180504的账单 依次类推
			//startTime=20180401
			//根据下一次执行时间获取完整的计费周期
			Date pullTime=nextBillTime;//拉取数据时间
			if(renterConfig.getPrevBillCreateTime()!=null){
				String nextBillTimeHHMMSS=DateUtils.format(nextBillTime,"HHmmss");
				if(nextBillTimeHHMMSS.equals("000000")){
					if(endTime==null){
						ChargeCycle preChargeCycle=getChargeCycleByCurrentTime(renterConfig.getPrevBillCreateTime(), chargeTimeInterval);
						ChargeCycle nextChargeCycle=getChargeCycleByCurrentTime(nextBillTime, chargeTimeInterval);
						String nextBillTimeYYYYMMDD=DateUtils.format(nextBillTime,"yyyyMMdd");
						String nextChargeCycleFromDateYYYYMMDD=DateUtils.format(nextChargeCycle.getFromDate(),"yyyyMMdd");
						if(!preChargeCycle.equals(nextChargeCycle)&&nextBillTimeYYYYMMDD.equals(nextChargeCycleFromDateYYYYMMDD)){//不是同一个周期，并且是下一周期的账单生成截止日
							pullTime=renterConfig.getPrevBillCreateTime();
							isBillCycleEnd=true;
						}
					}
					else{
						
						String nextBillTimeYYYYMMDD=DateUtils.format(nextBillTime, "yyyyMMdd");
						Date next=DateUtils.parse(nextBillTimeYYYYMMDD, "yyyyMMdd");
						if(next.getTime()>endTime.getTime()){
							pullTime=renterConfig.getEndTime();
							isBillCycleEnd=true;
							isRenterExpires=true;
						}
						else{
							ChargeCycle preChargeCycle=getChargeCycleByCurrentTime(renterConfig.getPrevBillCreateTime(), chargeTimeInterval);
							ChargeCycle nextChargeCycle=getChargeCycleByCurrentTime(nextBillTime, chargeTimeInterval);
							String nextChargeCycleFromDateYYYYMMDD=DateUtils.format(nextChargeCycle.getFromDate(),"yyyyMMdd");
							if(!preChargeCycle.equals(nextChargeCycle)&&nextBillTimeYYYYMMDD.equals(nextChargeCycleFromDateYYYYMMDD)){//不是同一个周期，并且是下一周期的账单生成截止日
								pullTime=renterConfig.getPrevBillCreateTime();
								isBillCycleEnd=true;
							}
						}
					}
					
				}
			}
			ChargeCycle chargeCycle=getChargeCycleByCurrentTime(pullTime, chargeTimeInterval);
			//根据startTime和endTime 计算实际周期
			Date fromDate=null;
			Date toDate=null;
			Date lastCheckDate=null;//上期抄表日期
			Date thisCheckDate=null;//本期抄表日期
			//根据startTime和endTime以及nextBillTime 计算实际查询数据的区间范围
			if(startTime.before(chargeCycle.getFromDate())){//区间之外
				fromDate=chargeCycle.getFromDate();
				lastCheckDate=chargeCycle.getFromDate();
				toDate=pullTime;
			}
			else{//区间之内
				fromDate=startTime;
				lastCheckDate=startTime;
				toDate=pullTime;
			}
			if(endTime==null){
				thisCheckDate=chargeCycle.getToDate();
			}
			else{
				if(endTime.before(chargeCycle.getToDate())){
					thisCheckDate=endTime;
				}
				else{
					thisCheckDate=chargeCycle.getToDate();
				}
			}
			List<RenterMeter> renterMeters = renterMeterService.getMeters(renterId);
			if (renterMeters.isEmpty()) {
				result.set(ResultCode.ERROR_202, "租客仪表不存在");
				return result;
			}
			// 获取租客当前周期内的电费和电量
			Set<String> meterIds=new HashSet<>();
			Map<String,Double> meterMap=new HashMap<>();
			for(RenterMeter renterMeter:renterMeters){
				meterIds.add(renterMeter.getMeterNo());
				meterMap.put(renterMeter.getMeterNo(), renterMeter.getRate()/100);
			}
			Double charge=0.00D;//总电费
			Double quantity=0.00D;//总电量
			ElectricChargeStatReqData electricChargeStatReqData=new ElectricChargeStatReqData();
			electricChargeStatReqData.setCompanyId(renter.getCompanyId());
			electricChargeStatReqData.setFromDate(DateUtils.format(fromDate, "yyyyMMdd"));
			electricChargeStatReqData.setToDate(DateUtils.format(toDate, "yyyyMMdd"));
			electricChargeStatReqData.setMeterIds(meterIds);
			electricChargeStatReqData.setType("MONTH");
			electricChargeStatReqData.setSource("DAY_LOG");
			StatListResult<ElectricChargeStat> statListResult=reportSearchDubboService.getElectricChargeStats(electricChargeStatReqData);
			if(!statListResult.isSuccess()){
				result.set(ResultCode.ERROR_401, statListResult.getResultMessage());
				return result;
			}
			//更新租客配置信息表
			renterConfig.setPrevBillCreateTime(nextBillTime);
			if(isRenterExpires){
				renterConfig.setNextBillCreateTime(nextBillTime);
			}
			else{
				renterConfig.setNextBillCreateTime(DateUtils.getDateOffsetMinute(nextBillTime, updateFrequency));	
			}
			renterConfig.setBillTaskStatus(0);
			renterConfig.setBillTaskExcuteTime(new Date());
			//生成/更细当前周期账单
			for(ElectricChargeStat electriciChargeStat:statListResult.getList()){
				Double rate=meterMap.get(electriciChargeStat.getMeterId());
				quantity+=rate*electriciChargeStat.getTotalElectric();
				if(renterConfig.getSubChargeMode()==RenterConfig.TIME_OF_USER_CHARGE_MODE){
					charge+=rate*electriciChargeStat.getApexElectric()*renterConfig.getApexPrice();
					charge+=rate*electriciChargeStat.getPeakElectric()*renterConfig.getPeakPrice();
					charge+=rate*electriciChargeStat.getFlatElectric()*renterConfig.getFlatPrice();
					charge+=rate*electriciChargeStat.getValleyElectric()*renterConfig.getValleyPrice();
				}
				else{
					charge+=rate*electriciChargeStat.getTotalElectric()*renterConfig.getBasePrice();
				}
				
			}
			RenterChargeOrder renterChargeOrder=renterOrderService.get(renterId, 
					DateUtils.format(lastCheckDate, "yyyyMMdd"), 
					DateUtils.format(thisCheckDate, "yyyyMMdd"));
			if(renterChargeOrder==null){
				renterChargeOrder=new RenterChargeOrder();
				renterChargeOrder.setOrderId(BizUtils.getOrderId("EC"));
				renterChargeOrder.setCompanyId(renter.getCompanyId());
				renterChargeOrder.setCharge(charge);
				renterChargeOrder.setQuantity(quantity);
				renterChargeOrder.setThisCheckDate(thisCheckDate);
				renterChargeOrder.setThisPeriodReading(0.00D);
				renterChargeOrder.setLastCheckDate(lastCheckDate);
				renterChargeOrder.setLastPeriodReading(0.00D);
				renterChargeOrder.setRenterId(renterId);
				renterChargeOrder.setPrepaidCharge(0.00D);
				renterChargeOrder.setPayStatus(PayStatus.UNPAY.getValue());
				renterChargeOrder.setStatus(RenterChargeOrder.GENERATION);
				renterOrderService.save(renterChargeOrder,renterConfig);
				//短信+消息中心推送
				sendMessagePushInfo(renterChargeOrder);
				
			}
			else{
				renterChargeOrder.setCharge(charge);
				renterChargeOrder.setQuantity(quantity);
				if(isBillCycleEnd){//表示账单截止
					renterChargeOrder.setStatus(RenterChargeOrder.SUCCESS);
				}
				renterOrderService.update(renterChargeOrder,renterConfig);
			}
		}
		catch(Exception e){
			logger.error("handlePrePaymentOrder",e);
			result.set(ResultCode.ERROR_500, "系统异常");
		}
		return result;
	}

	@Override
	public Result handleMonthlyPaymentOrder(Long renterId) {
		Result result = new DefaultResult();
		// 获取租客信息
	  	// 获取租客房东配置信息
		// 获取租客配置信息
		// 获取租客仪表信息
		// 获取租客计费周期信息
		// 获取租客信息
		try{
			Renter renter = renterService.getById(renterId);
			if (renter == null) {
				result.set(ResultCode.ERROR_201, "租客信息不存在");
				return result;
			}
			// 获取房东的配置信息，计费周期
			PowerPriceConfig powerPriceConfig = renterOrderService
					.getPowerPriceConfig(renter.getCompanyId());
			if (powerPriceConfig == null) {
				result.set(ResultCode.ERROR_201, "企业配置不存在");
				return result;
			}
			RenterConfig renterConfig = renterService.getRenterConfig(renterId);
			if (renterConfig == null) {
				result.set(ResultCode.ERROR_202, "租客配置不存在");
				return result;
			}
			if(renterConfig.getChargeMode()!=ChargeModeType.MONTH_SETTLE.getValue()){
				result.set(ResultCode.ERROR_202, "只支持月结算方式");
				return result;
			}
			if(renterConfig.getBillTaskStatus()!=BillTaskStatus.EXECUTING.getValue()){
				result.set(ResultCode.ERROR_202, "账单生成task状态有问题！");
				return result;
			}
			if(renterConfig.getNextBillCreateTime().getTime()>=new Date().getTime()){
				result.set(ResultCode.ERROR_202, "未到账单生成时间，无法生成！");
				return result;
			}
			Integer chargeTimeInterval= Integer.valueOf(powerPriceConfig.getChargeTimeInterval());
			Date startTime=renterConfig.getStartTime();
			Date endTime=renterConfig.getEndTime();
			int date=DateUtils.getDayOfMonth(startTime);
			Date nextBillTime=renterConfig.getNextBillCreateTime();
			//判断是否整月
			boolean isWhileMonth=false;
			//计算出当月数据的起始日期
			String fromDate="";
			String toDate="";
			//计算下一个月的账单生成日期
			Date nextNextBillTime=null;
			if(renterConfig.getPrevBillCreateTime()==null){
				//是否首月
				if(endTime==null){
					if(date==chargeTimeInterval){
						isWhileMonth=true;
					}
					else{
						fromDate=DateUtils.format(startTime, "yyyyMMdd");
						toDate=DateUtils.getDateOffsetDay(nextBillTime,-1, "yyyyMMdd");
					}
					nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetMonth(nextBillTime, 1, "yyyyMMdd"),"yyyyMMdd");
				}
				else{//nextBillTime和endTime进行比较
					//nextBillTime和endTime相等那么
					String nextBillTimeStr=DateUtils.getDateOffsetDay(nextBillTime, -1, "yyyyMMdd");
					String endTimeStr=DateUtils.format(endTime, "yyyyMMdd");
					Date next=DateUtils.parse(nextBillTimeStr, "yyyyMMdd");
					Date end=DateUtils.parse(endTimeStr, "yyyyMMdd");
					if(date==chargeTimeInterval&&next.getTime()<=end.getTime()){
						isWhileMonth=true;
					}
					else{
						fromDate=DateUtils.format(startTime, "yyyyMMdd");
						toDate=DateUtils.getDateOffsetDay(nextBillTime,-1, "yyyyMMdd");
					}
					if(next.getTime()<end.getTime()){
						nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetMonth(nextBillTime, 1, "yyyyMMdd"),"yyyyMMdd");
						Date temp=DateUtils.parse(DateUtils.getDateOffsetDay(nextNextBillTime, -1, "yyyyMMdd"), "yyyyMMdd");
						if(temp.getTime()>end.getTime()){
							nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetDay(end, 1, "yyyyMMdd"),"yyyyMMdd");
						}
					}
				}
			}
			else{
				if(endTime==null){
					isWhileMonth=true;
					nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetMonth(nextBillTime, 1, "yyyyMMdd"),"yyyyMMdd");
				}
				else{
					String nextBillTimeStr=DateUtils.getDateOffsetDay(nextBillTime, -1, "yyyyMMdd");
					String endTimeStr=DateUtils.format(endTime, "yyyyMMdd");
					Date next=DateUtils.parse(nextBillTimeStr, "yyyyMMdd");
					Date end=DateUtils.parse(endTimeStr, "yyyyMMdd");
					if(next.getTime()<end.getTime()){
						isWhileMonth=true;
					}
					else if(next.getTime()==end.getTime()){//下一次更新时间等于结束时间时，需要判断一下结束时间是不是当前周期内的时间
						ChargeCycle chargeCycle=this.getChargeCycleByCurrentTime(endTime, chargeTimeInterval);
						if(end.getTime()<chargeCycle.getToDate().getTime()){
							fromDate=DateUtils.getDateOffsetDay(renterConfig.getPrevBillCreateTime(),-1, "yyyyMMdd");
							toDate=DateUtils.getDateOffsetDay(nextBillTime,-1, "yyyyMMdd");
						}
						else{
							isWhileMonth=true;
						}
					}
					else{
						fromDate=DateUtils.getDateOffsetDay(renterConfig.getPrevBillCreateTime(),-1, "yyyyMMdd");
						toDate=DateUtils.getDateOffsetDay(nextBillTime,-1, "yyyyMMdd");
					}
					if(next.getTime()<end.getTime()){
						nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetMonth(nextBillTime, 1, "yyyyMMdd"),"yyyyMMdd");
						Date temp=DateUtils.parse(DateUtils.getDateOffsetDay(nextNextBillTime, -1, "yyyyMMdd"), "yyyyMMdd");
						if(temp.getTime()>end.getTime()){
							nextNextBillTime=DateUtils.parse(DateUtils.getDateOffsetDay(end, 1, "yyyyMMdd"),"yyyyMMdd");
						}
					}
					else{
						nextNextBillTime=nextBillTime;
					}
				}
			}
			
			List<RenterMeter> renterMeters = renterMeterService.getMeters(renterId);
			if (renterMeters.isEmpty()) {
				result.set(ResultCode.ERROR_202, "租客仪表不存在");
				return result;
			}
			
			// 获取租客当前周期内的电费和电量
			Set<String> meterIds=new HashSet<>();
			Map<String,Double> meterMap=new HashMap<>();
			for(RenterMeter renterMeter:renterMeters){
				meterIds.add(renterMeter.getMeterNo());
				meterMap.put(renterMeter.getMeterNo(), renterMeter.getRate()/100);
			}
			String type="";
			String source="";
			Double charge=0.00D;//总电费
			Double quantity=0.00D;//总电量
			Date lastCheckDate=null;//上期抄表日期
			Date thisCheckDate=null;//本期抄表日期
			if(isWhileMonth){
				fromDate=DateUtils.format(nextBillTime, "yyyyMM");
				toDate=DateUtils.format(nextBillTime, "yyyyMM");
				type="MONTH";
				source="MONTH_LOG";
				ChargeCycle chargeCycle=this.getChargeCycleByChargeMonth(fromDate, chargeTimeInterval);
				lastCheckDate=chargeCycle.getFromDate();
				thisCheckDate=chargeCycle.getToDate();
			}
			else{
				type="MONTH";
				source="DAY_LOG";
				lastCheckDate=DateUtils.parse(fromDate, "yyyyMMdd");
				thisCheckDate=DateUtils.parse(toDate,"yyyyMMdd");
			}
			ElectricChargeStatReqData electricChargeStatReqData=new ElectricChargeStatReqData();
			electricChargeStatReqData.setCompanyId(renter.getCompanyId());
			electricChargeStatReqData.setFromDate(fromDate);
			electricChargeStatReqData.setToDate(toDate);
			electricChargeStatReqData.setMeterIds(meterIds);
			electricChargeStatReqData.setType(type);
			electricChargeStatReqData.setSource(source);
			StatListResult<ElectricChargeStat> statListResult=reportSearchDubboService.getElectricChargeStats(electricChargeStatReqData);
			if(!statListResult.isSuccess()){
				result.set(ResultCode.ERROR_401, statListResult.getResultMessage());
				return result;
			}
			//更新租客配置信息表
			renterConfig.setPrevBillCreateTime(nextBillTime);
			renterConfig.setNextBillCreateTime(nextNextBillTime);
			renterConfig.setBillTaskStatus(BillTaskStatus.UNEXECUTED.getValue());
			renterConfig.setBillTaskExcuteTime(new Date());
			//生成/更细当前周期账单
			for(ElectricChargeStat electriciChargeStat:statListResult.getList()){
				Double rate=meterMap.get(electriciChargeStat.getMeterId());
				quantity+=rate*electriciChargeStat.getTotalElectric();
				if(renterConfig.getSubChargeMode()==RenterConfig.TIME_OF_USER_CHARGE_MODE){
					charge+=rate*electriciChargeStat.getApexElectric()*renterConfig.getApexPrice();
					charge+=rate*electriciChargeStat.getPeakElectric()*renterConfig.getPeakPrice();
					charge+=rate*electriciChargeStat.getFlatElectric()*renterConfig.getFlatPrice();
					charge+=rate*electriciChargeStat.getValleyElectric()*renterConfig.getValleyPrice();
				}
				else{
					charge+=rate*electriciChargeStat.getTotalElectric()*renterConfig.getBasePrice();
				}
				
			}
			if(renterOrderService.isExist(renterId, 
					DateUtils.format(lastCheckDate, "yyyyMMdd"), 
					DateUtils.format(thisCheckDate, "yyyyMMdd"))){
				result.set(ResultCode.ERROR_402, "账单已存在");
			}
			RenterChargeOrder renterChargeOrder=new RenterChargeOrder();
			renterChargeOrder.setOrderId(BizUtils.getOrderId("EC"));
			renterChargeOrder.setCompanyId(renter.getCompanyId());
			renterChargeOrder.setCharge(charge);
			renterChargeOrder.setQuantity(quantity);
			renterChargeOrder.setThisCheckDate(thisCheckDate);
			renterChargeOrder.setThisPeriodReading(0.00D);
			renterChargeOrder.setLastCheckDate(lastCheckDate);
			renterChargeOrder.setLastPeriodReading(0.00D);
			renterChargeOrder.setRenterId(renterId);
			renterChargeOrder.setPrepaidCharge(0.00D);
			if(MoneyUtils.greaterThan(charge, 0.00)){
				renterChargeOrder.setPayStatus(PayStatus.UNPAY.getValue());
			}
			else{
				renterChargeOrder.setPayStatus(PayStatus.SUCCESS.getValue());
			}
			renterChargeOrder.setStatus(RenterChargeOrder.SUCCESS);
			renterOrderService.save(renterChargeOrder,renterConfig);
			//账单生成消息和短信通知
			sendMessagePushInfo(renterChargeOrder);
		}
		catch(Exception e){
			logger.error("handleMonthlyPaymentOrder",e);
			result.set(ResultCode.ERROR_500, "系统异常");
		}
		return result;
	}
	/**
     * 创建一个固定线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());
    /**
     * 发送推送消息(异步多线程)
     * @param needOrder
     */
    private void sendMessagePushInfo(final RenterChargeOrder renterChargeOrder) {
        if (renterChargeOrder == null || renterChargeOrder.getRenterId() == null) {
            return;
        }
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
            	Renter renter = renterService.getById(renterChargeOrder.getRenterId());
    			if (renter == null) {
    				return ;
    			}
    			RenterConfig renterConfig = renterService.getRenterConfig(renter.getId());
    			if (renterConfig == null) {
    				return ;
    			}
    			GetUserInfoResult result=userInfoService.getUserInfo(renter.getMemberId());
    			if(!result.isSuccess()){
    				return ;
    			}
    			String mobile=result.getMemberInfo().getMobile();
    			Long uid=result.getMemberInfo().getUid();
    			Map<String,String> params=new HashMap<>();
    			int month=renterChargeOrder.getThisCheckDate().getMonth();
    			params.put("month", String.valueOf(month+1));
    			MessageId messageId=null;
    			if(renterConfig.getChargeMode()==ChargeModeType.PAY_FIRST.getValue()){
    				messageId=MessageId.PRE_PAYMENT_RECHARGE_ORDER_CREATE;
    			}
    			else if(renterConfig.getChargeMode()==ChargeModeType.MONTH_SETTLE.getValue()){
    				messageId=MessageId.MONTHLY_PAYMENT_RECHARGE_ORDER_CREATE;
    			}
    			else{
    				return ;
    			}
            	messageInfoService.sendSmsAndPushMessage(uid, mobile, MessageId.AUDIT_COUPON, params);
            }
        });
    }
	/**
	 * 根据计费周期月和计费周期日计算计费周期(开始时间和结束时间)
	 * @param chargeMonth
	 * @param chargeTimeInterval
	 * @return
	 */
	private ChargeCycle getChargeCycleByChargeMonth(String chargeMonth,Integer chargeTimeInterval){
		ChargeCycle chargeCycle=new ChargeCycle();
    	Date thisMonth=DateUtils.parse(chargeMonth, "yyyyMM");
    	Date lastMonth=DateUtils.getDateOffsetMonth(thisMonth, -1);
    	Date fromDate=DateUtils.getDateOffsetDay(lastMonth, chargeTimeInterval-1);
    	Date toDate=DateUtils.getDateOffsetDay(thisMonth, chargeTimeInterval-2);
    	chargeCycle.setFromDate(fromDate);
    	chargeCycle.setToDate(toDate);
		return chargeCycle;
	}
	/**
	 * 根据当前时间计算当前时间的计费周期
	 * @param currentTime
	 * @param chargeTimeInterval
	 * @return
	 */
	private ChargeCycle getChargeCycleByCurrentTime(Date currentTime,Integer chargeTimeInterval){
		ChargeCycle chargeCycle=new ChargeCycle();
		int date=DateUtils.getDayOfMonth(currentTime);
		String currMonth=DateUtils.format(currentTime, "yyyyMM");
		Date currMonthDate=DateUtils.parse(currMonth, "yyyyMM");
		Date fromDate=null;
		Date toDate=null;
		if(date>=chargeTimeInterval){//
			fromDate=DateUtils.getDateOffsetDay(currMonthDate, chargeTimeInterval-1);
			toDate=DateUtils.getDateOffsetDay(DateUtils.getDateOffsetMonth(currMonthDate, 1),chargeTimeInterval-2);
		}
		else{
			fromDate=DateUtils.getDateOffsetDay(DateUtils.getDateOffsetMonth(currMonthDate, -1),chargeTimeInterval-1);
			toDate=DateUtils.getDateOffsetDay(currMonthDate, chargeTimeInterval-2);
		}
    	chargeCycle.setFromDate(fromDate);
    	chargeCycle.setToDate(toDate);
		return chargeCycle;
	}
	class ChargeCycle{
		Date fromDate;
		Date toDate;
		public Date getFromDate() {
			return fromDate;
		}
		public Date getToDate() {
			return toDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
		public boolean equals(ChargeCycle obj){
			if(fromDate.equals(obj.getFromDate())&&toDate.equals(obj.getToDate())){
				return true;
			}
			return false;
		}
	}
	@Override
	public Result beforeHandleOrder(Long renterId) {
		// 锁住租客配置信息
		Result result = new DefaultResult();
		try {
			Renter renter = renterService.getById(renterId);
			if (renter == null) {
				result.set(ResultCode.ERROR_201, "租客信息不存在");
				return result;
			}
			RenterConfig renterConfig = renterService.getRenterConfig(renterId);
			if (renterConfig == null) {
				result.set(ResultCode.ERROR_202, "租客配置不存在");
				return result;
			}
			if(renterConfig.getBillTaskStatus()==BillTaskStatus.EXECUTING.getValue()){
				result.set(ResultCode.ERROR_202, "账单已经锁住了，请勿重复操作！");
				return result;
			}
			renterService.updateConfigBillTaskStatus(renterConfig.getId(), BillTaskStatus.EXECUTING.getValue());
			
		} catch (Exception e) {
			logger.error("beforeHandleOrder", e);
			result.set(ResultCode.ERROR_500, "系统异常");
		}
		return result;

	}

	@Override
	public Result afterHandlerOrder(Long renterId) {
		// 解锁租客配置信息(任务信息)
		// 锁住租客配置信息
		Result result = new DefaultResult();
		try {
			Renter renter = renterService.getById(renterId);
			if (renter == null) {
				result.set(ResultCode.ERROR_201, "租客信息不存在");
				return result;
			}
			RenterConfig renterConfig = renterService.getRenterConfig(renterId);
			if (renterConfig == null) {
				result.set(ResultCode.ERROR_202, "租客配置不存在");
				return result;
			}
			if (renterConfig.getBillTaskStatus() == BillTaskStatus.UNEXECUTED
					.getValue()) {
				result.set(ResultCode.ERROR_202, "账单已经锁住了，请勿重复操作！");
				return result;
			}
			renterService.updateConfigBillTaskStatus(renterConfig.getId(),
					BillTaskStatus.UNEXECUTED.getValue());

		} catch (Exception e) {
			logger.error("afterHandlerOrder", e);
			result.set(ResultCode.ERROR_500, "系统异常");
		}
		return result;
	}

	@Override
	public List<String> getPrePaymentUnsettledOrders(int limit) {
		//pscp_renter_config(charge_mode=1 预付费模式)和pscp_renter_charge_order(prepaid_charge<charge时才可以 status=0[这种情况，要考虑账单是否正在生成中] status=1[这种情况很简单])两张表联合查询 
		return renterOrderService.getPrePaymentUnsettledOrders(limit);
	}

/*	@Override
	public Result settlementPrepaymentOrder(String orderId) {
		Result result = new DefaultResult();
		try {
			RenterChargeOrder order=this.renterOrderService.getByOrderId(orderId);
			if(order==null){
				result.set(ResultCode.ERROR_201, "账单不存在");
				return result;
			}
			Renter renter = renterService.getById(order.getRenterId());
			if (renter == null) {
				result.set(ResultCode.ERROR_201, "租客信息不存在");
				return result;
			}
			RenterConfig renterConfig = renterService.getRenterConfig(order.getRenterId());
			if (renterConfig == null) {
				result.set(ResultCode.ERROR_202, "租客配置不存在");
				return result;
			}
			if (renterConfig.getBillTaskStatus() == BillTaskStatus.UNEXECUTED
					.getValue()) {
				result.set(ResultCode.ERROR_202, "账单已经锁住了，请勿稍后进行扣款操作！");
				return result;
			}
			
			
		} catch (Exception e) {
			logger.error("settlementPrepaymentOrder", e);
			result.set(ResultCode.ERROR_500, "系统异常");
		}
		return result;
	}*/

}
