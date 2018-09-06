package com.edianniu.pscp.das.meter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.meter.bean.ChargeInterval;
import com.edianniu.pscp.das.meter.bean.DemandInfo;
import com.edianniu.pscp.das.meter.bean.MeterLogDo;
import com.edianniu.pscp.das.meter.bean.PeriodInfo;
import com.edianniu.pscp.das.meter.bean.PowerChargeInfo;
import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;
import com.edianniu.pscp.das.meter.log.DWLogger;
import com.edianniu.pscp.das.meter.service.MeterLogAnalysisService;
import com.edianniu.pscp.das.meter.service.MeterLogService;
import com.edianniu.pscp.das.meter.service.MeterService;
import com.edianniu.pscp.das.meter.util.ChargeIntervalUtils;
import com.edianniu.pscp.das.meter.util.NumericUtils;
import com.edianniu.pscp.das.meter.util.PowerFactorUtils;
import com.edianniu.pscp.das.util.DateUtils;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricChargeVO;
/**
 * 仪表日志分析
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月24日 下午1:49:04 
 * @version V1.0
 */
@Service
@Repository("meterLogAnalysisService")
public class MeterLogAnalysisServiceImpl implements MeterLogAnalysisService{
	private static final Logger logger = LoggerFactory
			.getLogger(MeterLogAnalysisServiceImpl.class);
	private static final DWLogger dwLogger=new DWLogger();
	private final static String METER_DEMAND_DETAILS_CAHE_KEY="meter_demand_details";
	private final static String METER_LOG_START_CACHE_KEY="meter_log_start#";
	private final static String METER_LOG_END_CACHE_KEY="meter_log_end#";
	private final static String METER_DAY_DETAIL_LOG_CACHE_KEY="meter_day_detail_log#";
	private final static String METER_MONTH_LOG_START_CACHE_KEY="meter_month_log_start#";
	
	@Autowired
    private JedisUtil jedisUtil;
	@Autowired
	private MeterService meterService;
	@Autowired
	private MeterLogService meterLogService;
	@Autowired
	private ReportSearchDubboService reportSearchDubboService;
	@Autowired
	private ReportProduceDubboService reportProduceDubboService;
	private void dayVoltageCurrentDetail(MeterLogDo meterLogDo) {
		int period=15;
		Date reportTime=new Date(meterLogDo.getTime());
		String day=DateUtils.format(reportTime, "yyyyMMdd");
		Set<String> periodSet=getPeriodSet(day,period);
		String dtime=DateUtils.format(reportTime, "yyyyMMddHHmm");
		if(!periodSet.contains(dtime)){//不是这个范围的，忽略掉
			return ;
		}
		DayVoltageCurrentDetailReqData req=new DayVoltageCurrentDetailReqData();
		req.setMeterId(meterLogDo.getMeterId());
		req.setMeterType(meterLogDo.getMeterType());
		req.setCompanyId(meterLogDo.getCompanyId());
		req.setCompanyName(meterLogDo.getCompanyName());
		req.setSubTermCode(meterLogDo.getSubTermCode());
		req.setDate(day);
		req.setUa(meterLogDo.getUa());
		req.setUb(meterLogDo.getUb());
		req.setUc(meterLogDo.getUc());
		req.setuStatus(meterLogDo.getuStatus());
		req.setUaStatus(meterLogDo.getUaStatus());
		req.setUbStatus(meterLogDo.getUbStatus());
		req.setUcStatus(meterLogDo.getUcStatus());
		req.setIa(meterLogDo.getIa()*meterLogDo.getMultilpe());
		req.setIb(meterLogDo.getIb()*meterLogDo.getMultilpe());
		req.setIc(meterLogDo.getIc()*meterLogDo.getMultilpe());
		req.setiUnbalanceDegree(meterLogDo.getiUnbalanceDegree());
		req.setIaUnbalanceDegree(meterLogDo.getIaUnbalanceDegree());
		req.setIbUnbalanceDegree(meterLogDo.getIbUnbalanceDegree());
		req.setIcUnbalanceDegree(meterLogDo.getIcUnbalanceDegree());
		req.setPeriod(period);
		String hhmm=DateUtils.format(reportTime, "HH:mm");
		req.setTime(hhmm+":00");
		req.setCreateTime(new Date().getTime());
		meterLogService.saveDayVoltageCurrentDetail(req);
	}
	private Set<String> getPeriodSet(String yyyyMMdd,int period){
		long start=System.currentTimeMillis();
		Set<String> periodSet=new HashSet<String>();
		Date reportTime=DateUtils.parse(yyyyMMdd+"0000", "yyyyMMddHHmm");
		for(int i=0;i<24*60/period;i++){
			String yyyyMMddHHmm=DateUtils.getDateOffsetMinute(reportTime, period*i, "yyyyMMddHHmm");
			periodSet.add(yyyyMMddHHmm);
		}
		logger.info("getPeriodSet excute time= {} ms",(System.currentTimeMillis()-start));
		return periodSet;
	}
	/**
	 * 需量明细处理
	 * 按时间间隔计算电量
	 * 需量按结算周期计算
	 * @param companyPowerPriceConfig
	 * @param meterLogDo
	 */
	private void demandDetail(CompanyPowerPriceConfig companyPowerPriceConfig,
			MeterLogDo meterLogDo){
		Integer period=15;//15分钟一次
		//2018-01-05 00:00:00 2018-01-05 00:15:00
		//2018-01-05 00:15:00 2018-01-05 00:30:00
		//2018-01-05 00:30:00 2018-01-05 00:45:00
		//对日志数据进行分区 2018-01-05 15:35:xx
		String key=METER_DEMAND_DETAILS_CAHE_KEY;
		Date reportTime=new Date(meterLogDo.getTime());//当前时间属于哪个区间
		//根据reportTime获取nowTime
		String day=DateUtils.format(reportTime,"yyyyMMdd");
		String nowTime=DateUtils.format(reportTime, "yyyyMMddHHmm");
		PeriodInfo periodInfo=getPeriodInfo(day, period, reportTime);
		String nowPeriodTime=periodInfo.getTime();
		DemandInfo demandInfo=new DemandInfo();
		demandInfo.setPower(meterLogDo.getAbcActivePower());
		demandInfo.setTime(meterLogDo.getTime());
		jedisUtil.sadd(key+"#"+nowPeriodTime+"#"+meterLogDo.getMeterId(), JSON.toJSONString(demandInfo));
		Set<String> periodSet=getPeriodSet(day,period);
		if(!periodSet.contains(nowTime)){//不是这个范围的，忽略掉
			return ;
		}
		String prevTime=DateUtils.getDateOffsetMinute(DateUtils.parse(nowPeriodTime, "yyyyMMddHHmm"), -period, "yyyyMMddHHmm");
		Set<String> prevDataSets=jedisUtil.smembers(key+"#"+prevTime+"#"+meterLogDo.getMeterId());
		if(prevDataSets!=null&&!prevDataSets.isEmpty()){//nowTime:20180105 30:00 preTime:15:00
			//写入数据 preTime的数据
			Double totalPower=0.0D;
			for(String str:prevDataSets){
				DemandInfo di=JSON.parseObject(str, DemandInfo.class);
				totalPower=totalPower+di.getPower();
			}
			Double power=totalPower/prevDataSets.size();
			DemandDetailReqData req=new DemandDetailReqData();//key=yyyyMMdd hh:MM
			req.setMeterId(meterLogDo.getMeterId());
			req.setMeterType(meterLogDo.getMeterType());
			req.setSubTermCode(meterLogDo.getSubTermCode());
			req.setCompanyId(meterLogDo.getCompanyId());
			req.setCompanyName(meterLogDo.getCompanyName());
			req.setDate(prevTime);
			req.setTime(DateUtils.parse(prevTime, "yyyyMMddHHmm").getTime());
			req.setPower(power*meterLogDo.getMultilpe()/1000D);//KW 
			req.setType(1);
			req.setValue(period);
			req.setCreateTime(new Date().getTime());
			meterLogService.saveDemandDetail(req);
			jedisUtil.del(key+"#"+prevTime+"#"+meterLogDo.getMeterId());
		}
		
	}
	/**
	 * 获取一个周期内开始的记录
	 * @param meterLogDo
	 * @param date  yyyy|yyyyMM|yyyyMMdd
	 * @param lastDate 上一年|上一月|前一天
	 * @return
	 */
	private MeterLogDo getStartMeterLogDo(MeterLogDo meterLogDo,String date,String lastDate){
		String startMeterLogJson=jedisUtil.get(METER_LOG_START_CACHE_KEY+date+"#"+meterLogDo.getMeterId());
		MeterLogDo startMeterLog=null;//开始抄表记录
		if(StringUtils.isNotBlank(startMeterLogJson)){
			startMeterLog=JSON.parseObject(startMeterLogJson, MeterLogDo.class);	
		}
		else{
			String lastMonthMeterEndRead=jedisUtil.get(METER_LOG_START_CACHE_KEY+lastDate+"#"+meterLogDo.getMeterId());
			if(StringUtils.isNotBlank(lastMonthMeterEndRead)){
				startMeterLog=JSON.parseObject(lastMonthMeterEndRead, MeterLogDo.class);
				jedisUtil.set("METER_LOG_START_CACHE_KEY#"+date+"#"+meterLogDo.getMeterId(),JSON.toJSONString(meterLogDo));
			}
			else{
				startMeterLog=meterLogDo;
				jedisUtil.set(METER_LOG_START_CACHE_KEY+date+"#"+meterLogDo.getMeterId(),JSON.toJSONString(meterLogDo));
				/**设置结束抄表记录**/
				jedisUtil.set(METER_LOG_END_CACHE_KEY+lastDate+"#"+meterLogDo.getMeterId(),JSON.toJSONString(meterLogDo));
			}
		}
		return startMeterLog;
	}
	
	
	private void dayLoadDetail(MeterLogDo meterLogDo) {
		int period=15;
		Date reportTime=new Date(meterLogDo.getTime());
		String day=DateUtils.format(reportTime, "yyyyMMdd");
		Set<String> periodSet=getPeriodSet(day,period);
		String dtime=DateUtils.format(reportTime, "yyyyMMddHHmm");
		if(!periodSet.contains(dtime)){//不是这个范围的，忽略掉
			return ;
		}
		Double  currentLoad=meterLogDo.getAbcActivePower()*meterLogDo.getMultilpe()/1000D;//有功功率 KW
		DayLoadDetailReqData req=new DayLoadDetailReqData();
		req.setMeterId(meterLogDo.getMeterId());
		req.setMeterType(meterLogDo.getMeterType());
		req.setCompanyId(meterLogDo.getCompanyId());
		req.setCompanyName(meterLogDo.getCompanyName());
		req.setSubTermCode(meterLogDo.getSubTermCode());
		req.setDate(day);
		req.setLoad(currentLoad);
		req.setPeriod(period+"");
		String hhmm=DateUtils.format(reportTime, "HH:mm");
		req.setTime(hhmm+":00");
		req.setCreateTime(new Date().getTime());
		meterLogService.saveDayLoadDetail(req);
	}
	
	private PeriodInfo getPeriodInfo(String yyyyMMdd,int period,Date reportTime){
		List<PeriodInfo> list=this.getPeriodList(yyyyMMdd, period);
		for(PeriodInfo periodInfo:list){
			if(periodInfo.include(reportTime.getTime())){
				return periodInfo;
			}
		}
		return null;
	}
	private List<PeriodInfo> getPeriodList(String yyyyMMdd,int period){
		List<PeriodInfo> list=new ArrayList<PeriodInfo>();
		Date reportTime=DateUtils.parse(yyyyMMdd+"0000", "yyyyMMddHHmm");
		for(int i=0;i<24*60/period;i++){
			String time=DateUtils.getDateOffsetMinute(reportTime, period*i, "yyyyMMddHHmm");
			Long start=DateUtils.parse(time+"00", "yyyyMMddHHmmss").getTime();
			String nextTime=DateUtils.getDateOffsetMinute(reportTime, period*(i+1), "yyyyMMddHHmm");
			Long end=DateUtils.parse(nextTime+"00", "yyyyMMddHHmmss").getTime();
			PeriodInfo periodInfo=new PeriodInfo();
			periodInfo.setTime(time);
			periodInfo.setStart(start);
			periodInfo.setEnd(end);
			list.add(periodInfo);
		}
		return list;
	}
	/**
	 * 计算电量
	 * @param dayLogVo
	 * @param isNowDay true(当日)/false(前一日)
	 * @param nowLog
	 * @param multilpe
	 * @return
	 */
	private PowerChargeInfo getPowerChargeInfo(DayLogVO dayLogVo,boolean isNowDay,MeterLogDo nowLog,int multilpe){
		PowerChargeInfo powerChargeInfo=new PowerChargeInfo();
		Double lastTotal=0.00D;//上期抄表数
		Double lastApex=0.00D;//上期抄表数
		Double lastPeak=0.00D;//上期抄表数
		Double lastFlat=0.00D;//上期抄表数
		Double lastValley=0.00D;//上期抄表数
		Double lastReactiveTotal=0.00D;//上期抄表数
		if(dayLogVo!=null){
			if(isNowDay){
				lastTotal=dayLogVo.getLastTotal();
				lastApex=dayLogVo.getLastApex();
				lastPeak=dayLogVo.getLastPeak();
				lastFlat=dayLogVo.getLastFlat();
				lastValley=dayLogVo.getLastValley();
				lastReactiveTotal=dayLogVo.getLastReactiveTotal();
			}
			else{
				lastTotal=dayLogVo.getThisTotal();
				lastApex=dayLogVo.getThisApex();
				lastPeak=dayLogVo.getThisPeak();
				lastFlat=dayLogVo.getThisFlat();
				lastValley=dayLogVo.getThisValley();
				lastReactiveTotal=dayLogVo.getThisReactiveTotal();
			}
		}
		
		Double total=nowLog.getPositiveTotalActivePowerCharge()-lastTotal;	
		powerChargeInfo.setTotal(total*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastTotal(lastTotal);
		powerChargeInfo.setThisTotal(nowLog.getPositiveTotalActivePowerCharge());
		
		Double apex=nowLog.getPositiveApexActivePowerCharge()-lastApex;
		powerChargeInfo.setApex(apex*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastApex(lastApex);
		powerChargeInfo.setThisApex(nowLog.getPositiveApexActivePowerCharge());
		
		Double peak=nowLog.getPositivePeakPowerCharge()-lastPeak;
		powerChargeInfo.setPeak(peak*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastPeak(lastPeak);
		powerChargeInfo.setThisPeak(nowLog.getPositivePeakPowerCharge());
		
		Double flat=nowLog.getPositiveFlatPowerCharge()-lastFlat;
		powerChargeInfo.setFlat(flat*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastFlat(lastFlat);
		powerChargeInfo.setThisFlat(nowLog.getPositiveFlatPowerCharge());
		
		Double valley=nowLog.getPositiveValleyPowerCharge()-lastValley;
		powerChargeInfo.setValley(valley*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastValley(lastValley);
		powerChargeInfo.setThisValley(nowLog.getPositiveValleyPowerCharge());
		
		Double reactiveTotal=nowLog.getPositiveTotalReactivePowerCharge()-lastReactiveTotal;
		powerChargeInfo.setReactiveTotal(reactiveTotal*multilpe);
		/**记录读数**/
		powerChargeInfo.setLastReactiveTotal(lastReactiveTotal);
		powerChargeInfo.setThisReactiveTotal(nowLog.getPositiveTotalReactivePowerCharge());
		
		return powerChargeInfo;
	}
	/**
	 * 日电量数据:用电量，电费，最高负荷，实时负荷，实时电压电流以及实时功率因数
	 * @param priceConfig
	 * @param nowLogData
	 * @param dayMeterStartReadLog
	 */
	private void dayData(CompanyPowerPriceConfig priceConfig,MeterLogDo nowLogData){
		DayLogReqData req=new DayLogReqData();
		req.setCompanyId(nowLogData.getCompanyId());
		req.setMeterId(nowLogData.getMeterId());
		req.setMeterType(nowLogData.getMeterType());
		req.setSubTermCode(nowLogData.getSubTermCode());
		req.setCompanyName(nowLogData.getCompanyName());
		req.setDate(DateUtils.format(new Date(nowLogData.getTime()), "yyyyMMdd"));
		PowerChargeInfo powerChargeInfo=null;
		DayLogVO vo=reportSearchDubboService.getById(req);
		if(vo!=null){
			req.setApexPrice(vo.getApexPrice());// 尖单价
			req.setPeakPrice(vo.getPeakPrice());// 峰单价
			req.setFlatPrice(vo.getFlatPrice());// 平单价
			req.setValleyPrice(vo.getValleyPrice());// 谷单价
			req.setBasePrice(vo.getBasePrice());
			req.setCreateTime(vo.getCreateTime());
			req.setUpdateTime(new Date().getTime());
			//当日
			powerChargeInfo=getPowerChargeInfo(vo,true,nowLogData, nowLogData.getMultilpe());
		}
		else{
			DayLogReqData lastReq=new DayLogReqData();
			lastReq.setCompanyId(nowLogData.getCompanyId());
			lastReq.setMeterId(nowLogData.getMeterId());
			lastReq.setMeterType(nowLogData.getMeterType());
			lastReq.setSubTermCode(nowLogData.getSubTermCode());
			lastReq.setCompanyName(nowLogData.getCompanyName());
			lastReq.setDate(DateUtils.getDateOffsetDay(new Date(nowLogData.getTime()), -1, "yyyyMMdd"));	
			DayLogVO lastVo=reportSearchDubboService.getById(lastReq);
			//前一日
			powerChargeInfo=getPowerChargeInfo(lastVo,false,nowLogData, nowLogData.getMultilpe());
			req.setApexPrice(priceConfig.getApexPrice());// 尖单价
			req.setPeakPrice(priceConfig.getPeakPrice());// 峰单价
			req.setFlatPrice(priceConfig.getFlatPrice());// 平单价
			req.setValleyPrice(priceConfig.getValleyPrice());// 谷单价
			req.setBasePrice(priceConfig.getBasePrice());
			req.setCreateTime(new Date().getTime());
		}
		
		req.setTotal(powerChargeInfo.getTotal());// 总计电量
		req.setApex(powerChargeInfo.getApex());// 尖电量
		req.setPeak(powerChargeInfo.getPeak());//峰电量
		req.setFlat(powerChargeInfo.getFlat());// 平电量
		req.setValley(powerChargeInfo.getValley());// 谷电量
		req.setReactiveTotal(powerChargeInfo.getReactiveTotal());
		
		req.setLastTotal(powerChargeInfo.getLastTotal());
		req.setLastApex(powerChargeInfo.getLastApex());
		req.setLastPeak(powerChargeInfo.getLastPeak());
		req.setLastFlat(powerChargeInfo.getLastFlat());
		req.setLastValley(powerChargeInfo.getLastValley());
		req.setLastReactiveTotal(powerChargeInfo.getLastReactiveTotal());
		
		req.setThisTotal(powerChargeInfo.getThisTotal());
		req.setThisApex(powerChargeInfo.getThisApex());
		req.setThisPeak(powerChargeInfo.getThisPeak());
		req.setThisFlat(powerChargeInfo.getThisFlat());
		req.setThisValley(powerChargeInfo.getThisValley());
		req.setThisReactiveTotal(powerChargeInfo.getThisReactiveTotal());
		
		req.setApexCharge(req.getApex()*req.getApexPrice());//尖电费
		req.setPeakCharge(req.getPeak()*req.getPeakPrice());// 峰电费
		req.setFlatCharge(req.getFlat()*req.getFlatPrice());// 平电费
		req.setValleyCharge(req.getValley()*req.getValleyPrice());// 谷电费
		Integer chargeMode=priceConfig.getChargeMode();//电度电价计费方式 0普通，1分时
		if(chargeMode==CompanyPowerPriceConfig.TIME_OF_USER_CHARGE_MODE){
			req.setTotalCharge(req.getApexCharge()+req.getPeakCharge()+req.getFlatCharge()+req.getValleyCharge());//总计电费
		}
		else{
			if(req.getBasePrice()==null){
				req.setBasePrice(priceConfig.getBasePrice());
			}
			req.setTotalCharge(req.getTotal()*req.getBasePrice());
		}
		
		//Wp
		Double total=req.getTotal();
		//Wq
		Double reactiveTotal=req.getReactiveTotal();
		Double factor=this.getFactor(total, reactiveTotal);
		req.setFactor(factor);
		Double  currentLoad=nowLogData.getAbcActivePower();//有功功率
		Double  electric=req.getTotal();
		req.setElectric(electric);//用电量，电量是累计的
		req.setCurrentLoad(currentLoad*nowLogData.getMultilpe()/1000D);//KW
		req.setMaxLoad(currentLoad*nowLogData.getMultilpe()/1000D);//KW
		req.setDate(DateUtils.format(new Date(nowLogData.getTime()), "yyyyMMdd"));
		req.setUa(nowLogData.getUa());
		req.setUb(nowLogData.getUb());
		req.setUc(nowLogData.getUc());
		req.setuStatus(nowLogData.getuStatus());
		req.setUaStatus(nowLogData.getUaStatus());
		req.setUbStatus(nowLogData.getUbStatus());
		req.setUcStatus(nowLogData.getUcStatus());
		req.setIa(nowLogData.getIa()*nowLogData.getMultilpe());
		req.setIb(nowLogData.getIb()*nowLogData.getMultilpe());
		req.setIc(nowLogData.getIc()*nowLogData.getMultilpe());
		req.setiUnbalanceDegree(nowLogData.getiUnbalanceDegree());
		req.setIaUnbalanceDegree(nowLogData.getIaUnbalanceDegree());
		req.setIbUnbalanceDegree(nowLogData.getIbUnbalanceDegree());
		req.setIcUnbalanceDegree(nowLogData.getIcUnbalanceDegree());
		if(vo!=null){
			if(vo.getMaxLoad()<currentLoad*nowLogData.getMultilpe()/1000D){//更新最大负荷
				req.setMaxLoad(currentLoad*nowLogData.getMultilpe()/1000D);//KW
			}
			else{
				req.setMaxLoad(vo.getMaxLoad());
			}
			
		}
		meterLogService.saveDayLog(req);
	}
	/**
	 * 计算功率因数
	 * @param total
	 * @param reactiveTotal
	 * @return
	 */
	private Double getFactor(Double total,Double reactiveTotal){
		logger.info("计算功率因数begin:{},{}",total,reactiveTotal);
		 Double factor=0.00D;
		 if(total==null||reactiveTotal==null){
			 return factor;
		 }
		 if(!(reactiveTotal==0.00D&&total==0.00D)){
		    	factor=NumericUtils.format(total/Math.sqrt(Math.pow(total, 2)+Math.pow(reactiveTotal, 2)));
		 }
		 logger.info("计算功率因数end:{},{}",total,reactiveTotal);
		 return factor;
	}
	/**
	 * 日明细数据
	 * @param companyPowerPriceConfig
	 * @param nowLogData
	 */
	private void dayDetailData(CompanyPowerPriceConfig companyPowerPriceConfig,
			MeterLogDo nowLogData){
		Integer period=15;//15分钟一次
		//2018-01-05 00:00:00 2018-01-05 00:15:00
		//2018-01-05 00:15:00 2018-01-05 00:30:00
		//2018-01-05 00:30:00 2018-01-05 00:45:00
		//对日志数据进行分区 2018-01-05 15:35:xx
		String key=METER_DAY_DETAIL_LOG_CACHE_KEY;
		Date reportTime=new Date(nowLogData.getTime());
		String day= DateUtils.format(reportTime, "yyyyMMdd");
		String nowTime= DateUtils.format(reportTime, "yyyyMMddHHmm");
		Set<String> periodSet=getPeriodSet(day,period);
		if(!periodSet.contains(nowTime)){//不是这个范围的，忽略掉
			return ;
		}
		String nowDataKey=key+nowTime+"#"+nowLogData.getMeterId();
		if(!jedisUtil.exists(nowDataKey)){//第一个点
			//过期时间12小时
			jedisUtil.setex(nowDataKey, 12*60*60, JSON.toJSONString(nowLogData));
		}
		//判断上一次是否存在
		String prevDay=DateUtils.getDateOffsetMinute(reportTime, -period, "yyyyMMdd");
		String prevTime=DateUtils.getDateOffsetMinute(reportTime, -period, "yyyyMMddHHmm");
		String prevDataKey=key+prevTime+"#"+nowLogData.getMeterId();
		if(jedisUtil.exists(prevDataKey)){//nowTime:20180105 30:00 preTime:15:00
			String prevData=jedisUtil.get(prevDataKey);
			//写入数据 preTime的数据
			//nowData-prevData;
			MeterLogReqData prevLogData=JSON.parseObject(prevData, MeterLogReqData.class);
		    //根据数据的时间，计算分段类型，以及分段价格
			ChargeInterval chargeInterval=ChargeIntervalUtils.getChargeInterval(companyPowerPriceConfig, day,reportTime.getTime());
		    Integer chargeType=chargeInterval.getIntervalType();
		    Double subSectionPrice=chargeInterval.getIntervalPrice();
		    Long startTime=0L;
		    Long endTime=0L;
		    int multilpe=nowLogData.getMultilpe();
		    Integer rate=multilpe;
		   
		    Double start=prevLogData.getPositiveTotalActivePowerCharge();
		    Double end=nowLogData.getPositiveTotalActivePowerCharge();
		    Double electric=rate*(end-start);
		    Double fee=chargeInterval.getIntervalPrice()*electric;
		    Double total=nowLogData.getPositiveTotalActivePowerCharge()-prevLogData.getPositiveTotalActivePowerCharge();
		    Double reactiveTotal=nowLogData.getPositiveTotalReactivePowerCharge()-prevLogData.getPositiveTotalReactivePowerCharge();
		    Double factor=this.getFactor(total*rate, reactiveTotal*rate);
		    DayDetailLogReqData ecReq=new DayDetailLogReqData();//key=yyyyMMdd hh:MM
		    Date createTime=new Date();
			if(prevDay.equals(day)){//当天 TODO 
				startTime=prevLogData.getTime();
				endTime=nowLogData.getTime();
				ecReq.setDate(day);
			}
			else{//昨天，抄表结束日期需要调整一下 TODO
				startTime=prevLogData.getTime();
				endTime=nowLogData.getTime();
				ecReq.setDate(prevDay);
			}
			/**日电费明细数据**/
			ecReq.setMeterId(nowLogData.getMeterId());
			ecReq.setMeterType(nowLogData.getMeterType());
			ecReq.setSubTermCode(nowLogData.getSubTermCode());
			ecReq.setCompanyId(nowLogData.getCompanyId());
			ecReq.setCompanyName(nowLogData.getCompanyName());
			ecReq.setPeriod(period);
			ecReq.setStartTime(startTime);//抄表开始时间
			ecReq.setEndTime(endTime);//抄表结束时间
			ecReq.setStart(start);//抄表开始电量-不乘以倍率
			ecReq.setEnd(end);//抄表结束电量-不乘以倍率
			ecReq.setElectric(electric);//总电量
			ecReq.setRate(rate);//倍率
			ecReq.setChargeType(chargeType);//0普通,1尖,2峰,3平,4谷 
			ecReq.setPrice(subSectionPrice);
			ecReq.setFee(fee);
			ecReq.setCreateTime(createTime.getTime());
			ecReq.setFactor(factor);
			meterLogService.saveDayDetailLog(ecReq);
		}
		
	}
	/**
	 * 计算月电费数据
	 * @param meterInfoLog
	 * @param meterId
	 * @param companyId
	 * @param companyName
	 * @param multilpe
	 * @param meterType
	 * @param meterLogDo
	 */
	private void monthLog(
			CompanyPowerPriceConfig priceConfig,
			MeterLogDo meterLogDo) {
		
		Integer chargeMode=priceConfig.getChargeMode();//电度电价计费方式 0普通，1分时
		Integer baseChargeMode=priceConfig.getBaseChargeMode();//基本电费计价方式 1变压器容量，2最大需量
		Double transformerCapacityPrice=priceConfig.getTransformerCapacityPrice();//变压器容量单价
		Double transformerCapacity=priceConfig.getTransformerCapacity();//变压器容量/受电容量
		Double approvedCapacity=priceConfig.getMaxCapacity();//最大需量/核定需量
		Double maxCapacityPrice=priceConfig.getMaxCapacityPrice();//最大需量单价
		Double standardPowerFactor=priceConfig.getStandardAdjustRate();
		//TODO  只处理不按自然月的，未处理自然月的计费周期，后续再处理
		Integer chargeTimeInterval= Integer.valueOf(priceConfig.getChargeTimeInterval());
		//计算电费周期
		String cycle="";
		Date reportTime=new Date(meterLogDo.getTime());
		int date=DateUtils.getDayOfMonth(reportTime);
		String settleMentMonth="";//当前结算月份
		String cycleStart="";
		String cycleEnd="";
		if(date<chargeTimeInterval){
			cycleStart=DateUtils.getDateOffsetDay(DateUtils.getDateOffsetMonth(reportTime, -1),(chargeTimeInterval-date),"yyyyMMdd");
			cycleEnd=DateUtils.getDateOffsetDay(reportTime, (chargeTimeInterval-date-1), "yyyyMMdd");
			cycle=cycleStart+"~"+cycleEnd;
			settleMentMonth=DateUtils.getDateOffsetDay(reportTime, (chargeTimeInterval-date-1), "yyyyMM");
		}
		else{
			cycleStart=DateUtils.format(reportTime, "yyyyMM")+(chargeTimeInterval<=9?("0"+chargeTimeInterval):""+chargeTimeInterval);
			cycleEnd=DateUtils.getDateOffsetMonth(reportTime, 1,"yyyyMM")+((chargeTimeInterval-1)<=9?("0"+(chargeTimeInterval-1)):(""+(chargeTimeInterval-1)));
			cycle=cycleStart+"~"+cycleEnd;
			settleMentMonth=DateUtils.getDateOffsetMonth(reportTime, 1,"yyyyMM");
		}
		String settleMentLastMonth=DateUtils.getDateOffsetDay(DateUtils.parse(settleMentMonth, "yyyyMM"), -1, "yyyyMM");
		logger.debug("月电费计费数据,仪表编码:{},日期:{},本月:{},上月:{},周期:{}",
				meterLogDo.getMeterId(),DateUtils.format(reportTime),settleMentMonth,settleMentLastMonth,cycle);
		String startKey=METER_MONTH_LOG_START_CACHE_KEY+settleMentMonth+"#"+meterLogDo.getMeterId();
		if(!jedisUtil.exists(startKey)){//考虑并发
			jedisUtil.set(startKey, JSON.toJSONString(meterLogDo));
		}
		//判断当前时间是不是计费周期，当前月的数据是前一个月的计费周期
		MonthLogReqData req=new MonthLogReqData();
		req.setDate(settleMentMonth);
		req.setMeterId(meterLogDo.getMeterId());
		req.setMeterType(meterLogDo.getMeterType());
		req.setSubTermCode(meterLogDo.getSubTermCode());
		req.setCycle(cycle);
		req.setCompanyId(meterLogDo.getCompanyId());
		req.setCompanyName(meterLogDo.getCompanyName());
		//设置当月抄表日期-实时修改
		req.setThisMonthTotal(meterLogDo.getPositiveTotalActivePowerCharge());
		req.setThisMonthApex(meterLogDo.getPositiveApexActivePowerCharge());
		req.setThisMonthFlat(meterLogDo.getPositiveFlatPowerCharge());
		req.setThisMonthPeek(meterLogDo.getPositivePeakPowerCharge());
		req.setThisMonthValley(meterLogDo.getPositiveValleyPowerCharge());
		req.setThisMonthReativeTotal(meterLogDo.getPositiveTotalReactivePowerCharge());
		MonthElectricChargeVO monthElectricChargeVO=reportSearchDubboService.getById(req);
		if(monthElectricChargeVO!=null){//如果存在，只更新部分数据
			req.setLastMonthTotal(monthElectricChargeVO.getLastMonthTotal());
			req.setLastMonthApex(monthElectricChargeVO.getLastMonthApex());
			req.setLastMonthFlat(monthElectricChargeVO.getLastMonthFlat());
			req.setLastMonthPeek(monthElectricChargeVO.getLastMonthPeek());
			req.setLastMonthValley(monthElectricChargeVO.getLastMonthValley());
			req.setLastMonthReativeTotal(monthElectricChargeVO.getLastMonthReativeTotal());
			req.setBasicPrice(monthElectricChargeVO.getBasicPrice());
			//尖单价
			req.setApexPrice(monthElectricChargeVO.getApexPrice());
			//峰单价
			req.setFlatPrice(monthElectricChargeVO.getFlatPrice());
			//平单价
			req.setPeakPrice(monthElectricChargeVO.getPeakPrice());
			//谷单价
			req.setValleyPrice(monthElectricChargeVO.getValleyPrice());
			//计费周期
			req.setChargeMode(monthElectricChargeVO.getChargeMode());
			req.setBaseChargeMode(monthElectricChargeVO.getBaseChargeMode());
			//变压器容量单价
			if(monthElectricChargeVO.getCapacityPrice()==null){
				req.setCapacityPrice(transformerCapacityPrice);
			}
			else{
				req.setCapacityPrice(monthElectricChargeVO.getCapacityPrice());
			}
			//最大需量单价
			if(monthElectricChargeVO.getMaxCapacityPrice()==null){
				req.setMaxCapacityPrice(maxCapacityPrice);
			}
			else{
				req.setMaxCapacityPrice(monthElectricChargeVO.getMaxCapacityPrice());
			}
			//核定需量，实际需量，变压器容量
			req.setElectricalCapacity(monthElectricChargeVO.getElectricalCapacity());
			req.setStandardPowerFactor(monthElectricChargeVO.getStandardPowerFactor());
			req.setApprovedCapacity(monthElectricChargeVO.getApprovedCapacity());
			//计费周期
			req.setCycle(cycle);
			req.setCycle(monthElectricChargeVO.getCycle());
			req.setCreateTime(monthElectricChargeVO.getCreateTime());
			req.setUpdateTime(new Date().getTime());	
		}
		else{//如果不存在，则更新全部数据，需要获取上个月的最后抄表记录
			MonthLogReqData lastMonthElectricChargeReqData=new MonthLogReqData();
			lastMonthElectricChargeReqData.setDate(settleMentLastMonth);
			lastMonthElectricChargeReqData.setMeterId(meterLogDo.getMeterId());
			lastMonthElectricChargeReqData.setCompanyId(meterLogDo.getCompanyId());
			lastMonthElectricChargeReqData.setCompanyName(meterLogDo.getCompanyName());
			MonthElectricChargeVO lastMonthElectricChargeVO=reportSearchDubboService.getById(lastMonthElectricChargeReqData);
			if(lastMonthElectricChargeVO!=null){
				req.setLastMonthTotal(lastMonthElectricChargeVO.getThisMonthTotal());
				req.setLastMonthApex(lastMonthElectricChargeVO.getThisMonthApex());
				req.setLastMonthFlat(lastMonthElectricChargeVO.getThisMonthFlat());
				req.setLastMonthPeek(lastMonthElectricChargeVO.getThisMonthPeek());
				req.setLastMonthValley(lastMonthElectricChargeVO.getThisMonthValley());
				req.setLastMonthReativeTotal(lastMonthElectricChargeVO.getThisMonthReativeTotal());
				
			}
			else{//没有上一月数据1）默认0.00，查取上月的数据
				if(jedisUtil.exists(startKey)){
					String startData=jedisUtil.get(startKey);
					MeterLogDo startLog=JSON.parseObject(startData, MeterLogDo.class);
					req.setLastMonthTotal(startLog.getPositiveTotalActivePowerCharge());
					req.setLastMonthApex(startLog.getPositiveApexActivePowerCharge());
					req.setLastMonthFlat(startLog.getPositiveFlatPowerCharge());
					req.setLastMonthPeek(startLog.getPositivePeakPowerCharge());
					req.setLastMonthValley(startLog.getPositiveValleyPowerCharge());
					req.setLastMonthReativeTotal(startLog.getPositiveTotalReactivePowerCharge());
				}
				else{
					req.setLastMonthTotal(0.00D);
					req.setLastMonthApex(0.00D);
					req.setLastMonthFlat(0.00D);
					req.setLastMonthPeek(0.00D);
					req.setLastMonthValley(0.00D);
					req.setLastMonthReativeTotal(0.00D);
				}
				
			}
			//基本单价
			req.setBasicPrice(priceConfig.getBasePrice());
			//尖单价
			req.setApexPrice(priceConfig.getApexPrice());
			//峰单价
			req.setFlatPrice(priceConfig.getFlatPrice());
			//平单价
			req.setPeakPrice(priceConfig.getPeakPrice());
			//谷单价
			req.setValleyPrice(priceConfig.getValleyPrice());
			
			req.setChargeMode(chargeMode);
			req.setBaseChargeMode(baseChargeMode);
			//变压器容量单价
			req.setCapacityPrice(transformerCapacityPrice);
			req.setMaxCapacityPrice(maxCapacityPrice);
			//核定需量，实际需量，变压器容量
			req.setElectricalCapacity(transformerCapacity);
			req.setStandardPowerFactor(standardPowerFactor);
			req.setApprovedCapacity(approvedCapacity);
			//计费周期
			req.setCycle(cycle);
			req.setCreateTime(new Date().getTime());
		}
		int multilpe=meterLogDo.getMultilpe();
		//尖电量
		Double apex=req.getThisMonthApex()-req.getLastMonthApex();
		req.setApex(apex*multilpe);
		//尖电费
		Double apexPrice=req.getApexPrice();
		req.setApexCharge(apex*multilpe*apexPrice);
		//峰电量
		Double flat=req.getThisMonthFlat()-req.getLastMonthFlat();
		req.setFlat(flat*multilpe);
		//峰电费
		Double flatCharge=req.getFlat()*req.getFlatPrice();
		req.setFlatCharge(flatCharge);
		//平电量
		Double peak=req.getThisMonthPeek()-req.getLastMonthPeek();
		req.setPeak(peak*multilpe);
		//平电费
		Double peakCharge=req.getPeak()*req.getPeakPrice();
		req.setPeakCharge(peakCharge);
		//谷电量
		Double valley=req.getThisMonthValley()-req.getLastMonthValley();
		req.setValley(valley*multilpe);
		//谷电费
		Double valleyCharge=req.getValley()*req.getValleyPrice();
		req.setValleyCharge(valleyCharge);
		//基本电费-计费方式
		//最大需量：一个计费周期内区间(默认15分钟)功率平均值的最大值 
		//调用es查询接口，查询计费周期内的最大功率平均值即最大需量
		//根据最大需量
		//求出最大需量
		req.setBasicCharge(0.00D);
		if(meterLogDo.getMeterType()==1&&meterLogDo.getReferRoom()==1){//只有主线并且是配电房的需要计算基本电费
			Long startTime=DateUtils.parse(cycleStart+"000000","yyyyMMddHHmmss").getTime();
			Long endTime=DateUtils.parse(cycleEnd+"235959","yyyyMMddHHmmss").getTime();
			Double actualDemand=reportSearchDubboService.getMaxPower(meterLogDo.getCompanyId(), meterLogDo.getMeterId(), startTime,endTime);
			req.setActualDemand(actualDemand);
			if(req.getBaseChargeMode()==1){//变压器容量计费
				req.setBasicCharge(req.getElectricalCapacity()*req.getCapacityPrice());
			}
			else if(req.getBaseChargeMode()==2){//最大需量计费
				if(actualDemand<req.getElectricalCapacity()*0.4){
					req.setBasicCharge(req.getElectricalCapacity()*0.4*req.getMaxCapacityPrice());
				}
				else if(actualDemand<=req.getApprovedCapacity()&&actualDemand>=req.getElectricalCapacity()*0.4){
					req.setBasicCharge(actualDemand*req.getMaxCapacityPrice());
				}
				else{
					req.setBasicCharge(req.getApprovedCapacity()*req.getMaxCapacityPrice()+(actualDemand-req.getApprovedCapacity())*req.getMaxCapacityPrice()*2);
				}
			}
		}
		//电度电费
		Double total=req.getThisMonthTotal()-req.getLastMonthTotal();
		req.setTotal(total*multilpe);//乘以倍率
		Double reactiveTotal=req.getThisMonthReativeTotal()-req.getLastMonthReativeTotal();
		req.setReactiveTotal(reactiveTotal*multilpe);
		Double totalCharge=0.00;
		if(req.getChargeMode()==CompanyPowerPriceConfig.TIME_OF_USER_CHARGE_MODE){//分时
			totalCharge=req.getApexCharge()+
					req.getPeakCharge()+req.getFlatCharge()+
					req.getValleyCharge();
		}
		else{//普通
			totalCharge=req.getTotal()*req.getBasicPrice();
		}
		//基本电费
		if((meterLogDo.getMeterType()==1&&meterLogDo.getReferRoom()==1)){//只有主线并且是配电房的需要加上基本电费
			totalCharge=totalCharge+req.getBasicCharge();
		}
		Double factor=getFactor(req.getTotal(), req.getReactiveTotal());
		req.setActivePowerFactor(factor);
		//根据力调标准，算出力调电费
		if((meterLogDo.getMeterType()==1&&meterLogDo.getReferRoom()==1)){
			Double adjustmentRate=NumericUtils.format(PowerFactorUtils.getAdjustmentRate(standardPowerFactor, factor));
			Double factorCharge=totalCharge*adjustmentRate/100;
			req.setFactorCharge(factorCharge);
			req.setAdjustmentRate(adjustmentRate);
			totalCharge=totalCharge+factorCharge;
		}
		else{
			req.setFactorCharge(0.00D);
			req.setAdjustmentRate(0.00D);
		}
		//总电费
		req.setTotalCharge(totalCharge);
		meterLogService.saveMonthLog(req);
	}
	@Override
	public boolean doAnalysis(MeterLogDo meterLogDo) throws Exception {
		//转换为数据
		long start1=System.currentTimeMillis();
		Date reportTime=new Date(meterLogDo.getTime());
		MeterLogReqData meterLogReqData=new MeterLogReqData();//
		BeanUtils.copyProperties(meterLogReqData, meterLogDo);
		meterLogReqData.setCreateTime(meterLogDo.getTime());
		OpResult opResult=meterLogService.saveMeterLog(meterLogReqData);
		if(!opResult.isSuccess()){
			return true;
		}
		String day=DateUtils.format(reportTime, "yyyyMMdd");
		String yesterDay=DateUtils.getDateOffsetDay(new Date(meterLogDo.getTime()), -1, "yyyyMMdd");
		Long companyId=meterLogDo.getCompanyId();
		logger.info("meterLog,excute time: {} ms:",(System.currentTimeMillis()-start1));
		CompanyPowerPriceConfig companyPowerPriceConfig=meterService.getByCompanyId(companyId);
		if(companyPowerPriceConfig==null){
			logger.error("company power price config not found:{}",companyId);
			dwLogger.log("[companyPowerPriceConfig]",JSON.toJSONString(meterLogDo));
			return true;
		}
		//计算15分钟日电压电流明细数据
		long start2=System.currentTimeMillis();
		dayVoltageCurrentDetail(meterLogDo);
		logger.info("dayVoltageCurrentDetail,excute time: {} ms:",(System.currentTimeMillis()-start2));
		//计算15分钟功率平均值
		long start3=System.currentTimeMillis();
		demandDetail(companyPowerPriceConfig, meterLogDo);
		logger.info("demandDetail,excute time: {} ms:",(System.currentTimeMillis()-start3));
		/**日负荷明细**/
		long start5=System.currentTimeMillis();
		dayLoadDetail(meterLogDo);
		logger.info("dayLoadDetail,excute time: {} ms:",(System.currentTimeMillis()-start5));
		/**月电费数据(包括月用电量以及功率因数)**/
		long start6=System.currentTimeMillis();
		monthLog(companyPowerPriceConfig,
				meterLogDo);
		logger.info("monthLog,excute time: {} ms:",(System.currentTimeMillis()-start6));
		/**日数据**/
		long start11=System.currentTimeMillis();
		/**设置日开始抄表记录**/			
		//MeterLogDo startLog=getStartMeterLogDo(meterLogDo, day, yesterDay);
		dayData(companyPowerPriceConfig,meterLogDo);
		logger.info("dayLog,excute time: {} ms:",(System.currentTimeMillis()-start11));
		/**日明细数据**/
		long start12=System.currentTimeMillis();
		dayDetailData(companyPowerPriceConfig,meterLogDo);
		logger.info("dayDetailLog,excute time: {} ms:",(System.currentTimeMillis()-start12));
		
		
		return true;
	}
}
