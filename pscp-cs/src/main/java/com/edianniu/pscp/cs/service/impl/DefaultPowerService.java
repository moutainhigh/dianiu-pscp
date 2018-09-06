package com.edianniu.pscp.cs.service.impl;

import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.edianniu.pscp.cs.bean.DefaultResult;
import com.edianniu.pscp.cs.bean.power.*;
import com.edianniu.pscp.cs.bean.power.vo.*;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.dao.CompanyCustomerDao;
import com.edianniu.pscp.cs.dao.CompanyDao;
import com.edianniu.pscp.cs.dao.CompanyEquipmentDao;
import com.edianniu.pscp.cs.dao.CompanyLineDao;
import com.edianniu.pscp.cs.dao.CompanyMeterDao;
import com.edianniu.pscp.cs.dao.CompanyWarningDao;
import com.edianniu.pscp.cs.dao.PowerOtherConfigDao;
import com.edianniu.pscp.cs.dao.PowerPriceConfigDao;
import com.edianniu.pscp.cs.domain.Company;
import com.edianniu.pscp.cs.domain.CompanyCustomer;
import com.edianniu.pscp.cs.service.PowerService;
import com.edianniu.pscp.cs.util.BigDecimalUtil;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.cs.util.MoneyUtils;
import com.edianniu.pscp.message.bean.WarningType;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.SortOrder;
import com.edianniu.pscp.search.support.meter.list.DayAggregateListReqData;
import com.edianniu.pscp.search.support.meter.list.DayDetailLogListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.DayLoadDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayPowerFactorDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayVoltageCurrentDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricChargeListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.VoltageListReqData;
import com.edianniu.pscp.search.support.meter.vo.*;
import com.edianniu.pscp.search.support.meter.vo.VoltageVO;
import com.edianniu.pscp.search.support.meter.*;

/**
 * @author zhoujianjian
 * @date 2017年12月7日 下午3:52:20
 */
@Service
@Transactional
@Repository("powerService")
public class DefaultPowerService implements PowerService {
	
	// 获取数据时间间隔，默认15分钟
	@Value(value = "${time.period}")
	private int period;
	
	// 获取判断电流是否平衡的标准
	@Value(value = "${current.unbalance.standard}")
	private double currentUnbalanceStandard;
	
	@Autowired
	private ReportSearchDubboService reportSearchDubboService;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private CompanyLineDao companyLineDao;
	@Autowired
	private CompanyMeterDao companyMeterDao;
	@Autowired
	private CompanyWarningDao companyWarningDao;
	@Autowired
	private CompanyCustomerDao companyCustomerDao;
	@Autowired
	private CompanyEquipmentDao companyEquipmentDao;
	@Autowired
	private PowerOtherConfigDao powerOtherConfigDao;
	@Autowired
	private PowerPriceConfigDao powerPriceConfigDao;
	
	/**
	 * 客户线路
	 */
	@Override
	public List<LineVO> getCompanyLines(Long companyId) {
		if (null == companyId){
			return new ArrayList<>();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		List<CompanyLineInfo> list = companyLineDao.queryList(map);
		List<LineVO> lineVOs = new ArrayList<>();
		for (CompanyLineInfo companyLineInfo : list) {
			LineVO lineVO = new LineVO();
			lineVO.setId(companyLineInfo.getId());
			lineVO.setName(companyLineInfo.getName());
			lineVO.setParentId(companyLineInfo.getParentId());
			// 主线排在前面，子线路排在后面
			if (companyLineInfo.getParentId() == 0L) {
				lineVOs.add(0, lineVO);
			} else {
				lineVOs.add(lineVO);
			}
		}
		return lineVOs;
	}

	/**
	 * 获取企业主表信息
	 * @param companyId
	 * @param lineId  可为空
	 * @return
	 */
	public DefaultResult getCompanyMainMeterId(Long companyId, Long lineId){
		DefaultResult result = new DefaultResult();
		if (null == companyId) {
			result.set(ResultCode.ERROR_201, "companyId不能为空");
			return result;
		}
		// 获取线路信息
		CompanyLineInfo line = null;
		if (null == lineId) {
			// 默认获取企业总线
			line = getCustomerMainLine(companyId);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("id", lineId);
			line = companyLineDao.queryObject(map);
		}
		if (null == line) {
			result.set(ResultCode.ERROR_201, "客户线路不存在");
			return result;
		}
		// 获取总线仪表编号
		String meterId = line.getMeterNo();
		if (null == meterId) {
			result.set(ResultCode.ERROR_201, "客户线路未绑定仪表");
			return result;
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("meterId", meterId);
		map.put("multiple", line.getMultiple());
		map.put("lineId", line.getId());
		map.put("lineName", line.getName());
		result.setObject(map);
		return result;
	}
	
	/**
	 * 用电负荷
	 */
	@Override
	public PowerLoadResult getPowerLoad(Long companyId) {
		PowerLoadResult result = new PowerLoadResult();
		// 获取企业主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取今天日期和昨天日期yyyyMMdd
		Date now = new Date();
		String today = DateUtils.format(now, DateUtils.DATE_PATTERN).replace("-", "");
		String yesterday = DateUtils.getYesterdayDate(today);
		if (null == today || null == yesterday) {
			result.set(ResultCode.ERROR_201, "日期转换异常");
			return result;
		}
		
		// 今日负荷信息
		DayLoadDetailListReqData req = new DayLoadDetailListReqData();
		req.setCompanyId(companyId);
		req.setDate(today);
		req.setMeterId(meterId);
		req.setPageSize(24 * 60 / 15);
		req.addSort("time", SortOrder.ASC);
		ReportListResult<DayLoadDetailVO> loadVoResult = reportSearchDubboService.search(req);
		if (! loadVoResult.isSuccess()) {
			result.set(loadVoResult.getResultCode(), loadVoResult.getResultMessage());
			return result;
		}
		List<DayLoadDetailVO> loadVolist = loadVoResult.getList();
		LoadVO load = new LoadVO();
		String presentLoad = "0.00";
		String maxLoadOfToday = "0.00";
		if (CollectionUtils.isNotEmpty(loadVolist)) {
			presentLoad = BizUtils.doubleToString(loadVolist.get(loadVolist.size() - 1).getLoad());
			//今日最高负荷
			Optional<DayLoadDetailVO> opt= loadVolist.stream().max(Comparator.comparing(DayLoadDetailVO ::getLoad));
			maxLoadOfToday = BizUtils.doubleToString(opt.get().getLoad());
		} 
		load.setPresent(presentLoad);
		result.setMaxLoadOfToday(maxLoadOfToday);
		
		// 负荷等级配置     low低   1    economic经济  2    warn警戒  3   over越界 4 
		List<Type> limits = new ArrayList<>();
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyId", companyId);
		queryMap.put("type", PowerConfigType.LOAD.getValue());
		List<PowerOtherConfigInfo> configList = powerOtherConfigDao.queryConfigs(queryMap);
		if (CollectionUtils.isEmpty(configList)) {
			result.set(ResultCode.ERROR_201, "客户尚未配置负荷等级");
			return result;
		}
		for (PowerOtherConfigInfo config : configList) {
			Type type = new Type(config.getKey(), config.getKeyName());
			String[] split = config.getValue().split("-");
			if (split.length != 2) {
				result.set(ResultCode.ERROR_201, "客户负荷等级配置不合法");
				return result;
			}
			String sub = BigDecimalUtil.sub(split[1], split[0]);
			type.setValue(sub);
			limits.add(type);
		}
		load.setLimits(limits);
		result.setLoad(load);
		
		// 昨日负荷信息  （仅日期不同，其他条件相同）
		req.setDate(yesterday);
		ReportListResult<DayLoadDetailVO> loadVoResultOfYesterday = reportSearchDubboService.search(req);
		if (! loadVoResultOfYesterday.isSuccess()) {
			result.set(loadVoResultOfYesterday.getResultCode(), loadVoResultOfYesterday.getResultMessage());
			return result;
		}
		List<DayLoadDetailVO> loadVoListOfYesterday = loadVoResultOfYesterday.getList();
		String maxLoadOfLastDay = "0.00";
		if (CollectionUtils.isNotEmpty(loadVoListOfYesterday)) {
			// 昨日最高负荷 
			Optional<DayLoadDetailVO> opt= loadVoListOfYesterday.stream().max(Comparator.comparing(DayLoadDetailVO ::getLoad));
			maxLoadOfLastDay = BizUtils.doubleToString(opt.get().getLoad());
		} 
		result.setMaxLoadOfLastDay(maxLoadOfLastDay);
		
		// 本月电费、本月用电量（有功）和功率因数
		MonthElectricChargeListReqData chargeReqData = new MonthElectricChargeListReqData();
		chargeReqData.setCompanyId(companyId);
		chargeReqData.setMeterId(meterId);
		chargeReqData.setDate(today.substring(0, 6));
		chargeReqData.setPageSize(1);
		ReportListResult<MonthElectricChargeVO> chargeResult = reportSearchDubboService.search(chargeReqData);
		if (! chargeResult.isSuccess()) {
			result.set(chargeResult.getResultCode(), chargeResult.getResultMessage());
			return result;
		}
		List<MonthElectricChargeVO> chargeList = chargeResult.getList();
		String chargeOfThisMonth = "0.00";
		String quantityOfThisMonth = "0.00";
		String powerFactor = "0.00";
		if (CollectionUtils.isNotEmpty(chargeList)) {
			MonthElectricChargeVO chargeVO = chargeList.get(0);
			chargeOfThisMonth= BizUtils.doubleToString(chargeVO.getTotalCharge());
			quantityOfThisMonth= BizUtils.doubleToString(chargeVO.getTotal());
			powerFactor= BizUtils.doubleToString(chargeVO.getActivePowerFactor());
		} 
		result.setChargeOfThisMonth(chargeOfThisMonth);
		result.setQuantityOfThisMonth(quantityOfThisMonth);
		result.setPowerFactor(powerFactor);
		
		// 更新频率
		result.setFrequency(5000);
		return result;
	}

	/**
	 * 用电量
	 */
	@Override
	public PowerQuantityResult getPowerQuantity(Long companyId, String date) {
		PowerQuantityResult result = new PowerQuantityResult();
		// 获取企业主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 构建查询日期（本月、上月），精确到月份yyyyMM
		String thisMonth= buildSearchDateToMonth(date);
		String lastMonth = DateUtils.getLastMonth(thisMonth);
		if (null == thisMonth || null == lastMonth) {
			result.set(ResultCode.ERROR_201, "时间转换异常");
			return result;
		}
		
		// 本月每日用电量
		DayElectricListReqData req = new DayElectricListReqData();
		req.setCompanyId(companyId);
		req.setMeterId(meterId);
		req.setPageSize(31);
		req.addSort("date", SortOrder.ASC);
		req.setDate(thisMonth);
		ReportListResult<DayElectricVO> resultOfThisMonth = reportSearchDubboService.search(req);
		if (!resultOfThisMonth.isSuccess()) {
			result.set(resultOfThisMonth.getResultCode(), resultOfThisMonth.getResultMessage());
			return result;
		}
		List<DayElectricVO> listOfThisMonth = resultOfThisMonth.getList();
		List<QuantitiesOfMonth> quantitiesOfThisMonths = buildQuantitiesOfMonth(listOfThisMonth, thisMonth);
		result.setQuantitiesOfThisMonths(quantitiesOfThisMonths);
		// 本月最高
		if (CollectionUtils.isNotEmpty(listOfThisMonth)) {
			Optional<DayElectricVO> opt1= listOfThisMonth.stream().max(Comparator.comparing(DayElectricVO ::getTotal));
			result.setMaxQuantityOfThisMonth(BizUtils.doubleToString(opt1.get().getTotal()));
		} else {
			result.setMaxQuantityOfThisMonth("-");
		}
		
		
		// 上月每日用电量(仅月份不同，其他查询条件不变)
		req.setDate(lastMonth);
		ReportListResult<DayElectricVO> resultOfLastMonth = reportSearchDubboService.search(req);
		if (!resultOfLastMonth.isSuccess()) {
			result.set(resultOfLastMonth.getResultCode(), resultOfLastMonth.getResultMessage());
			return result;
		}
		List<DayElectricVO> listOfLastMonth = resultOfLastMonth.getList();
		List<QuantitiesOfMonth> quantitiesOfLastMonths = buildQuantitiesOfMonth(listOfLastMonth, lastMonth);
		result.setQuantitiesOfLastMonths(quantitiesOfLastMonths);
		// 上月最高
		if (CollectionUtils.isNotEmpty(listOfLastMonth)) {
			Optional<DayElectricVO> opt2 = listOfLastMonth.stream().max(Comparator.comparing(DayElectricVO::getTotal));
			result.setMaxQuantityOfLastMonth(BizUtils.doubleToString(opt2.get().getTotal()));
		} else {
			result.setMaxQuantityOfLastMonth("-");
		}
		
		
		return result;
	}
	
	/**
	 * 构建每月用电量
	 * @param listOfMonth  每月用电量数据集合
	 * @param yyyyMM       查询月份
	 */
	public List<QuantitiesOfMonth> buildQuantitiesOfMonth(List<DayElectricVO> listOfMonth, String yyyyMM){
		List<QuantitiesOfMonth> quantitiesOfMonths = new ArrayList<>();
		Map<String, DayElectricVO> voMap = new HashMap<>();
		for (DayElectricVO vo : listOfMonth) {
			String day = vo.getDate().substring(6);  // 截取日期
			voMap.put(day, vo);
		}
		// 根据年份和月份获取天数
		int days = DateUtils.getDaysByYearMonth(Integer.valueOf(yyyyMM.substring(0, 4)), Integer.valueOf(yyyyMM.substring(4)));
		List<String> dayList = new ArrayList<>(days);
		for (int i = 1; i <= days; i++) {
			dayList.add(i < 10 ? ("0" + i) : ("" + i));
		}
		for (String day : dayList) {
			QuantitiesOfMonth quantity = new QuantitiesOfMonth(day, null);
			if (voMap.containsKey(day)) {
				quantity.setQuantity(BizUtils.doubleToString(voMap.get(day).getTotal()));
			} else {
				quantity.setQuantity("0.00");
			}
			quantitiesOfMonths.add(quantity);
		}
		return quantitiesOfMonths;
	}
	

	/**
	 * 电量分布
	 */
	@Override
	public PowerDistributeResult getPowerDistribute(Long companyId, String date) {
		PowerDistributeResult result = new PowerDistributeResult();
		// 获取主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 构建查询月份
		String month= buildSearchDateToMonth(date);
		if (null == month) {
			result.set(ResultCode.ERROR_201, "时间转换异常");
			return result;
		}
		// 获取电度电费计费方式  ：  1分时       0普通
		Map<String, Object> map1 = new HashMap<>();
		map1.put("companyId", companyId);
		PowerPriceConfigInfo config = powerPriceConfigDao.queryObject(map1);
		if (null == config) {
			result.set(ResultCode.ERROR_201, "客户未配置电费参数");
			return result;
		}
		
		// 获取用户所有设备，并获得仪表编号数组
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyId", companyId);
		List<CompanyEquipmentInfo> equipments = companyEquipmentDao.queryList(queryMap);
		String[] meterIds = new String[equipments.size()];
		for (int i = 0; i < equipments.size(); i++) {
			meterIds[i] = equipments.get(i).getMeterId();
		}
		
		// 本月电量分布
		MonthElectricReqData reqData = new MonthElectricReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(month);
		reqData.setMeterId(meterId);
		SubEnergyReq subEnergyReq = new SubEnergyReq();
		subEnergyReq.setNeedSubEnergy(true);
		subEnergyReq.setMeterIds(meterIds);
		MonthElectricVO electricVO = reportSearchDubboService.getById(reqData, subEnergyReq);
		List<Type> quantities = new ArrayList<>();
		Type quantity1 = new Type(EquipmentType.POWER.getValue(), EquipmentType.POWER.getDesc(), "0.00");
		Type quantity2 = new Type(EquipmentType.LIGHTING.getValue(), EquipmentType.LIGHTING.getDesc(), "0.00");
		Type quantity3 = new Type(EquipmentType.AIR_CONDITIONER.getValue(), EquipmentType.AIR_CONDITIONER.getDesc(), "0.00");
		Type quantity4 = new Type(EquipmentType.SPECIAL.getValue(), EquipmentType.SPECIAL.getDesc(), "0.00");
		List<Type> charges = new ArrayList<>();
		Type charge1 = new Type(TimeType.JIAN.getValue(), TimeType.JIAN.getDesc(), "0.00");
		Type charge2 = new Type(TimeType.FENG.getValue(), TimeType.FENG.getDesc(), "0.00");
		Type charge3 = new Type(TimeType.PING.getValue(), TimeType.PING.getDesc(), "0.00");
		Type charge4 = new Type(TimeType.GU.getValue(),   TimeType.GU.getDesc(),   "0.00");
		if (null != electricVO) {
			quantity1.setValue(BizUtils.doubleToString(electricVO.getPower()));
			quantity2.setValue(BizUtils.doubleToString(electricVO.getLighting()));
			quantity3.setValue(BizUtils.doubleToString(electricVO.getAir()));
			quantity4.setValue(BizUtils.doubleToString(electricVO.getSpecial()));
			charge1.setValue(BizUtils.doubleToString(electricVO.getApexCharge()));
			charge2.setValue(BizUtils.doubleToString(electricVO.getPeakCharge()));
			charge3.setValue(BizUtils.doubleToString(electricVO.getFlatCharge()));
			charge4.setValue(BizUtils.doubleToString(electricVO.getValleyCharge()));
		}
		quantities.add(quantity1);
		quantities.add(quantity2);
		quantities.add(quantity3);
		quantities.add(quantity4);
		charges.add(charge1);
		charges.add(charge2);
		charges.add(charge3);
		charges.add(charge4);
		
		result.setQuantities(quantities);
		result.setChargeMode(config.getChargeMode());
		if (config.getChargeMode() == ChargeModeType.NORMAL.getValue()){
			result.setCharges(new ArrayList<>());
			return result;
		} 
		result.setCharges(charges);
		return result;
	}
	
	/**
	 * 功率因数
	 */
	@Override
	public PowerFactorResult getPowerFactor(Long companyId, Long lineId) {
		PowerFactorResult result = new PowerFactorResult();
		// 获取仪表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, lineId);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取当前日期
		Date now = new Date();
		String yyyyMMdd = DateUtils.format(now, DateUtils.DATE_PATTERN).replace("-", "");
		String yyyyMM = yyyyMMdd.substring(0, 6);
		
		// 当月功率因数、减免金额及减免率
		MonthLogReqData reqData = new MonthLogReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(yyyyMM);
		reqData.setMeterId(meterId);
		MonthElectricChargeVO monthElectricChargeVO = reportSearchDubboService.getById(reqData);
		String powerFactor = "0.00";
		String awardRate = "0.00";
		String awardMoney = "0.00";
		if (null != monthElectricChargeVO) {
			powerFactor = BizUtils.doubleToString(monthElectricChargeVO.getActivePowerFactor());
			awardMoney = BizUtils.doubleToString(Math.abs(monthElectricChargeVO.getFactorCharge()));
			awardRate = BizUtils.doubleToString(Math.abs(monthElectricChargeVO.getAdjustmentRate()));
			//奖惩   如果实际功率因数高于力调标准，则奖励(+号);否则为惩罚(-号)
			boolean isAward = monthElectricChargeVO.getActivePowerFactor() - monthElectricChargeVO.getStandardPowerFactor() >= 0;
			awardMoney = isAward ? ("+" + awardMoney) : ("-" + awardMoney);
			awardRate = isAward ? ("+" + awardRate) : ("-" + awardRate);
		}   
		result.setPowerFactor(powerFactor);
		result.setAwardRate(awardRate);
		result.setAwardMoney(awardMoney);
		
		// 日功率因数明细
		DayPowerFactorDetailListReqData req = new DayPowerFactorDetailListReqData();
		req.setCompanyId(companyId);
		req.setDate(yyyyMMdd);
		req.setMeterId(meterId);
		req.setPageSize(60 / period * 24);
		req.setPeriod(period);
		req.addSort("startTime", SortOrder.ASC);
		ReportListResult<DayPowerFactorDetailVO> dayResult = reportSearchDubboService.search(req);
		if (!dayResult.isSuccess()) {
			result.set(dayResult.getResultCode(), dayResult.getResultMessage());
			return result;
		}
		
		// 获取powerFactors
		List<PowerFactor> powerFactors = new ArrayList<>();
		List<DayPowerFactorDetailVO> dayList = dayResult.getList();
		Map<String, DayPowerFactorDetailVO> voMap = new HashMap<>();
		for (DayPowerFactorDetailVO vo : dayList) {
			String time = DateUtils.timeStampFormatStr(vo.getEndTime(), DateUtils.HHmm) + ":00";
			voMap.put(time, vo);
		}
		// 以00:00:00为起点，构建时间节点集合
		List<String> timeList = BizUtils.timeListFormat("00:00:00", period, DateUtils.HHmmss);
		for (String time : timeList) {
			PowerFactor pf = new PowerFactor(time, null);
			if (voMap.containsKey(time)) {
				pf.setPowerFactor(BizUtils.doubleToString(voMap.get(time).getFactor()));
			} else {
				pf.setPowerFactor("-");
			}
			powerFactors.add(pf);
		}
		result.setPowerFactors(powerFactors);
		
		// 获取powerFactorOfNow
		String powerFactorOfNow = "0.00";
		if (CollectionUtils.isNotEmpty(dayList)) {
			DayPowerFactorDetailVO lastVO = dayList.get(dayList.size() - 1);
			powerFactorOfNow = BizUtils.doubleToString(lastVO.getFactor());
		} 
		result.setPowerFactorOfNow(powerFactorOfNow);
		
		// 功率因数等级配置    serious严重  1    low低  2    normal正常  3    well好  4
		List<Type> limits = new ArrayList<>();
		Map<String, Object> queryConfigMap = new HashMap<>();
		queryConfigMap.put("companyId", companyId);
		queryConfigMap.put("type", PowerConfigType.POWER_FACTOR.getValue());
		List<PowerOtherConfigInfo> configList = powerOtherConfigDao.queryConfigs(queryConfigMap);
		if (CollectionUtils.isEmpty(configList)) {
			result.set(ResultCode.ERROR_201, "客户未配置功率等级");
			return result;
		}
		for (PowerOtherConfigInfo config : configList) {
			Type type = new Type(config.getKey(), config.getKeyName());
			String[] split = config.getValue().split("-");
			if (split.length != 2) {
				result.set(ResultCode.ERROR_201, "客户功率等级配置不合法");
				return result;
			}
			String sub = BigDecimalUtil.sub(split[1], split[0]);
			type.setValue(sub);
			limits.add(type);
		}
		result.setLimits(limits);
		
		// 获得客户所有监测点功率因数 
		List<CollectorPointVO> collectorPoints = new ArrayList<>();
		DayPowerFactorReqData req2 = new DayPowerFactorReqData();
		req2.setCompanyId(companyId);
		req2.setDate(yyyyMMdd);
		
		// 查询客户所有的监测点
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyId", companyId);
		List<CompanyMeterInfo> meterList = companyMeterDao.queryList(queryMap);
		if (CollectionUtils.isNotEmpty(meterList)) {
			for (CompanyMeterInfo meter : meterList) {
				CollectorPointVO collectorPointVO = new CollectorPointVO();
				// 每次仅仪表编号不同，其他查询信息不变
				req2.setMeterId(meter.getMeterId());
				DayPowerFactorVO dayPowerFactorVO = reportSearchDubboService.getById(req2);
				String pFactor = "0.00";
				if (null != dayPowerFactorVO) {
					pFactor = BizUtils.doubleToString(dayPowerFactorVO.getFactor());
				}
				
				collectorPointVO.setId(meter.getId());
				collectorPointVO.setName(meter.getName());
				collectorPointVO.setPowerFactor(pFactor);
				collectorPointVO.setAddress(meter.getEquipmentAddress());
				collectorPointVO.setType(meter.getEquipmentType());
				EquipmentType type = EquipmentType.getByValue(meter.getEquipmentType());
				String equipmentTypeName = type == null ? null :type.getDesc();
				collectorPointVO.setTypeName(equipmentTypeName);
				collectorPoints.add(collectorPointVO);
			}
		}
		result.setCollectorPoints(collectorPoints);
		return result;
	}
	
	
	/**
	 * 电费单
	 * @param date  精确到月份
	 * @return
	 */
	@Override
	public ChargeBillResult getChargeBill(Long companyId, String date) {
		ChargeBillResult result = new ChargeBillResult();
		// 获取主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 构建查询日期，精确到月份
		String month = buildSearchDateToMonth(date);
		// 获取电度电费计费方式  ：  1分时       0普通
		Map<String, Object> map1 = new HashMap<>();
		map1.put("companyId", companyId);
		PowerPriceConfigInfo config = powerPriceConfigDao.queryObject(map1);
		if (null == config) {
			result.set(ResultCode.ERROR_201, "客户未配置电费参数");
			return result;
		}
		
		// 月电费单
		MonthLogReqData reqData = new MonthLogReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(month);
		reqData.setMeterId(meterId);
		MonthElectricChargeVO chargeVO = reportSearchDubboService.getById(reqData);
		List<Charge> charges = new ArrayList<>();
		Charge charge0 = new Charge(ChargeType.COUNT.getValue(), ChargeType.COUNT.getDesc(), "0.00");
		Charge charge1 = new Charge(ChargeType.JIAN.getValue(), ChargeType.JIAN.getDesc(), "0.00");
		Charge charge2 = new Charge(ChargeType.FENG.getValue(), ChargeType.FENG.getDesc(), "0.00");
		Charge charge3 = new Charge(ChargeType.PING.getValue(), ChargeType.PING.getDesc(), "0.00");
		Charge charge4 = new Charge(ChargeType.GU.getValue(), ChargeType.GU.getDesc(), "0.00");
		Charge charge5 = new Charge(ChargeType.BASIC.getValue(), ChargeType.BASIC.getDesc(), "0.00");
		String award = "0.00";
		String totalCharge = "0.00";
		String period = "";
		if (null != chargeVO) {
			charge0.setCharge(BizUtils.doubleToString(chargeVO.getBasicPrice() * chargeVO.getTotal()));//统一单价*总有功电量
			charge1.setCharge(BizUtils.doubleToString(chargeVO.getApexCharge()));
			charge2.setCharge(BizUtils.doubleToString(chargeVO.getPeakCharge()));
			charge3.setCharge(BizUtils.doubleToString(chargeVO.getFlatCharge()));
			charge4.setCharge(BizUtils.doubleToString(chargeVO.getValleyCharge()));
			charge5.setCharge(BizUtils.doubleToString(chargeVO.getBasicCharge()));
			// 奖惩   如果实际功率因数大于力调标准，则奖励，为正号（+）
			boolean isAward = chargeVO.getActivePowerFactor() - chargeVO.getStandardPowerFactor() >= 0;
			award = BizUtils.doubleToString(Math.abs(chargeVO.getFactorCharge()));
			award = isAward ? ("+" + award) : ("-" + award);
			totalCharge = BizUtils.doubleToString(chargeVO.getTotalCharge());
			period = chargeVO.getCycle();
		}
		result.setChargeMode(config.getChargeMode());
		if (config.getChargeMode() == ChargeModeType.NORMAL.getValue()) {
			charges.add(charge0);
		} else {
			charges.add(charge1);  charges.add(charge2);
			charges.add(charge3);  charges.add(charge4);
		}
		charges.add(charge5);
		result.setCharges(charges);
		result.setAward(award);
		result.setPeriod(period);
		result.setTotalChargeOfThisMonth(totalCharge);
		
		return result;
	}
	
	
	/**
	 * 电费明细
	 * @param companyId
	 * @param fcompanyId
	 * @param date
	 * @return
	 */
	@Override
	public ChargeDetailResult getChargeDetail(Long companyId, String date) {
		ChargeDetailResult result = new ChargeDetailResult();
		// 获取主表meterId和倍率
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		Integer multiple = (Integer) map.get("multiple");
		// 构建查询日期，精确到月份
		String month = buildSearchDateToMonth(date);
		// 获取电度电费计费方式  ：  1分时       0普通
		Map<String, Object> map1 = new HashMap<>();
		map1.put("companyId", companyId);
		PowerPriceConfigInfo config = powerPriceConfigDao.queryObject(map1);
		if (null == config) {
			result.set(ResultCode.ERROR_201, "客户未配置电费参数");
			return result;
		}
		
		// 查询
		MonthLogReqData reqData = new MonthLogReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(month);
		reqData.setMeterId(meterId);
		MonthElectricChargeVO monthElectricChargeVO = reportSearchDubboService.getById(reqData);
		String totalCharge = "0.00";
		String period = "";
		String standardPowerFactor = "0.00";
		String activePowerFactor = "0.00";
		String actualDemand = "0.00";
		String verifyDemand = "0.00";
		String electricCapacity = "0.00";
		/*计费项目*/
		List<CountItem> countItems = new ArrayList<>();
		CountItem item0 = new CountItem(ChargeType.COUNT.getValue(), ChargeType.COUNT.getDesc(), "0.00", "0.00", "0.00", null);
		CountItem item1 = new CountItem(ChargeType.JIAN.getValue(), ChargeType.JIAN.getDesc(), "0.00", "0.00", "0.00", null);
		CountItem item2 = new CountItem(ChargeType.FENG.getValue(), ChargeType.FENG.getDesc(), "0.00", "0.00", "0.00", null);
		CountItem item3 = new CountItem(ChargeType.PING.getValue(), ChargeType.PING.getDesc(), "0.00", "0.00", "0.00", null);
		CountItem item4 = new CountItem(ChargeType.GU.getValue(), ChargeType.GU.getDesc(), "0.00", "0.00", "0.00", null);
		CountItem item5 = new CountItem(ChargeType.BASIC.getValue(), ChargeType.BASIC.getDesc(), null, "0.00", "0.00", null);
		CountItem item6 = new CountItem(ChargeType.FACTOR.getValue(), ChargeType.FACTOR.getDesc(), null, null, "0.00", null);
		/*抄表项*/
		List<MeterReadingItem> meterReadingItems = new ArrayList<>();
		MeterReadingItem meter1 = new MeterReadingItem(MeterReadingItemType.ACTIVE_TOTAL.getValue(), MeterReadingItemType.ACTIVE_TOTAL.getDesc(), null, null, multiple, "0.00");
		MeterReadingItem meter2 = new MeterReadingItem(MeterReadingItemType.ACTIVE_JIAN.getValue(), MeterReadingItemType.ACTIVE_JIAN.getDesc(), null, null, multiple, "0.00");
		MeterReadingItem meter3 = new MeterReadingItem(MeterReadingItemType.ACTIVE_FENG.getValue(), MeterReadingItemType.ACTIVE_FENG.getDesc(), null, null, multiple, "0.00");
		MeterReadingItem meter4 = new MeterReadingItem(MeterReadingItemType.ACTIVE_PING.getValue(), MeterReadingItemType.ACTIVE_PING.getDesc(), null, null, multiple, "0.00");
		MeterReadingItem meter5 = new MeterReadingItem(MeterReadingItemType.ACTIVE_GU.getValue(), MeterReadingItemType.ACTIVE_GU.getDesc(), null, null, multiple, "0.00");
		MeterReadingItem meter6 = new MeterReadingItem(MeterReadingItemType.REACTIVE_TOTAL.getValue(), MeterReadingItemType.REACTIVE_TOTAL.getDesc(), null, null, multiple, "0.00");
		if (monthElectricChargeVO != null) {
			/*总电费、计费周期、力调标准、实际力率、实际需量、核定需量、受电容量*/
			totalCharge = BizUtils.doubleToString(monthElectricChargeVO.getTotalCharge());
			period = monthElectricChargeVO.getCycle();
			standardPowerFactor = BizUtils.doubleToString(monthElectricChargeVO.getStandardPowerFactor());
			activePowerFactor = BizUtils.doubleToString(monthElectricChargeVO.getActivePowerFactor());
			actualDemand = BizUtils.doubleToString(monthElectricChargeVO.getActualDemand());
			verifyDemand = BizUtils.doubleToString(monthElectricChargeVO.getApprovedCapacity());
			electricCapacity = BizUtils.doubleToString(monthElectricChargeVO.getElectricalCapacity());
			/*设置计费项目:计费数量、单价和金额*/
			// 电度电费
			Double countPrice = monthElectricChargeVO.getBasicPrice();
			Double countCharge = countPrice * monthElectricChargeVO.getTotal(); // 统一单价*总有功电量
			Double countCount = (countPrice == 0.00) ? 0.00 : (countCharge / countPrice);
			item0.setCountNumPriceCharge(BizUtils.doubleToString(countCount), BizUtils.doubleToString(countPrice), BizUtils.doubleToString(countCharge));
			// 尖电费
			Double apexPrice = monthElectricChargeVO.getApexPrice();
			Double apexCharge = monthElectricChargeVO.getApexCharge();
			Double apexCount = (apexPrice == 0.00) ? 0.00 : (apexCharge / apexPrice);
			item1.setCountNumPriceCharge(BizUtils.doubleToString(apexCount), BizUtils.doubleToString(apexPrice), BizUtils.doubleToString(apexCharge));
			// 峰电费
			Double peakPrice = monthElectricChargeVO.getPeakPrice();
			Double peakCharge = monthElectricChargeVO.getPeakCharge();
			Double peakCount = (peakPrice == 0.00) ? 0.00 : (peakCharge / peakPrice);
			item2.setCountNumPriceCharge(BizUtils.doubleToString(peakCount), BizUtils.doubleToString(peakPrice), BizUtils.doubleToString(peakCharge));
			// 平电费
			Double flatPrice = monthElectricChargeVO.getFlatPrice();
			Double flatCharge = monthElectricChargeVO.getFlatCharge();
			Double flatCount = (flatPrice == 0.00) ? 0.00 : (flatCharge / flatPrice);
			item3.setCountNumPriceCharge(BizUtils.doubleToString(flatCount), BizUtils.doubleToString(flatPrice), BizUtils.doubleToString(flatCharge));
			// 谷电费
			Double valleyPrice = monthElectricChargeVO.getValleyPrice();
			Double valleyCharge = monthElectricChargeVO.getValleyCharge();
			Double valleyCount = (valleyPrice == 0.00) ? 0.00 : (valleyCharge / valleyPrice);
			item4.setCountNumPriceCharge(BizUtils.doubleToString(valleyCount), BizUtils.doubleToString(valleyPrice), BizUtils.doubleToString(valleyCharge));
			// 基本电费
			Double basicPrice = 0.00;
			Integer baseChargeMode = monthElectricChargeVO.getBaseChargeMode();
			if (null != baseChargeMode) {
				if (BaseChargeModeType.MAX_CAPACITY.getValue() == baseChargeMode) {
					basicPrice = monthElectricChargeVO.getCapacityPrice();
				}
				if (BaseChargeModeType.MAX_DEMAND.getValue() == baseChargeMode) {
					basicPrice = monthElectricChargeVO.getMaxCapacityPrice();
				}
			}
			Double basicCharge = monthElectricChargeVO.getBasicCharge();
			item5.setCountNumPriceCharge(null, BizUtils.doubleToString(basicPrice), BizUtils.doubleToString(basicCharge));
			Double factorCharge = monthElectricChargeVO.getFactorCharge();
			// 力调电费
			item6.setCountNumPriceCharge(null, null, BizUtils.doubleToString(factorCharge));
			/*设置抄表项 :上期示数、本期示数、电量*/
			meter1.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthTotal()));
			meter1.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthTotal()));
			meter1.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getTotal()));
			meter2.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthApex()));
			meter2.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthApex()));
			meter2.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getApex()));
			meter3.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthPeek()));
			meter3.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthPeek()));
			meter3.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getPeak()));
			meter4.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthFlat()));
			meter4.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthFlat()));
			meter4.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getFlat()));
			meter5.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthValley()));
			meter5.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthValley()));
			meter5.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getValley()));
			meter6.setLastPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getLastMonthReativeTotal()));
			meter6.setThisPeriodReading(BizUtils.doubleToString(monthElectricChargeVO.getThisMonthReativeTotal()));
			meter6.setQuantity(BizUtils.doubleToString(monthElectricChargeVO.getReactiveTotal()));
		}
		result.setChargeMode(config.getChargeMode());
		if (config.getChargeMode() == ChargeModeType.NORMAL.getValue()) {
			countItems.add(item0);
		} else {
			countItems.add(item1);  countItems.add(item2);
			countItems.add(item3);  countItems.add(item4);
		}
		countItems.add(item5);
		countItems.add(item6);
		result.setCountItems(countItems);
		meterReadingItems.add(meter1);
		if (config.getChargeMode() == ChargeModeType.DIVIDE_TIME.getValue()) {
			meterReadingItems.add(meter2);   meterReadingItems.add(meter3);
			meterReadingItems.add(meter4);   meterReadingItems.add(meter5);
		} 
		meterReadingItems.add(meter6);
		result.setMeterReadingItems(meterReadingItems);
		result.setTotalCharge(totalCharge);
		result.setTotalChargeOfHan(MoneyUtils.moneyToChinese(totalCharge));
		result.setPeriod(period);
		result.setStandardAdjustRate(standardPowerFactor);
		result.setActualAdjustRate(activePowerFactor);
		result.setVerifyDemand(verifyDemand);
		result.setElectricCapacity(electricCapacity);
		result.setActualDemand(actualDemand);
		return result;
	}
	
	
	/**
	 * 客户设备告警新增
	 */
	@Override
	public void saveWarning(WarningSaveReqData reqData, Long companyId) {
		WarningInfo info = new WarningInfo();
		info.setCompanyId(companyId);
		info.setWarningType(WarningType.GATEWAY_OFFLINE.getValue());
		info.setWarningObject(reqData.getWarningObject());
		Date occurTime = DateUtils.parse(reqData.getOccurTime(), DateUtils.DATE_TIME_PATTERN);
		info.setOccurTime(occurTime);
		info.setDescription(reqData.getDescription());
		info.setDealStatus(Constants.TAG_NO);
		info.setReadStatus(Constants.TAG_NO);
		info.setCreateUser("系统");
		companyWarningDao.save(info);
	}
	
	/**
	 * 告警列表
	 */
	@Override
	public PageResult<WarningVO> getWarningList(Long companyId, Integer offset, Integer pageSize) {
		PageResult<WarningVO> result = new PageResult<>();
		if (null == companyId)
			return result;
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("offset", offset);
		map.put("limit", pageSize);
		
		int total = companyWarningDao.queryTotal(map);
		int nextOffset = 0;
		if (total > 0) {
			List<WarningInfo> entityList = companyWarningDao.queryList(map);
			List<WarningVO> voList = new ArrayList<>();
			for (WarningInfo warningInfo : entityList) {
				WarningVO vo = new WarningVO();
				vo.setId(warningInfo.getId());
				vo.setType(WarningType.getByValue(warningInfo.getWarningType()).getDesc());
				if (warningInfo.getWarningType().equals(WarningType.GATEWAY_OFFLINE.getValue())) {
					vo.setObject(warningInfo.getWarningObject() + "号网关");
				} else { // 除设备掉线之外的告警类型
					String meterId = warningInfo.getWarningObject();
					Map<String, Object> queryMap = new HashMap<>();
					queryMap.put("meterId", meterId);
					CompanyMeterInfo companyMeterInfo = companyMeterDao.queryObject(queryMap );
					if (null != companyMeterInfo) {
						vo.setObject(companyMeterInfo.getName());
					}
				}
				vo.setDescription(warningInfo.getDescription());
				vo.setDealStatus(warningInfo.getDealStatus());
				vo.setReadStatus(warningInfo.getReadStatus());
				Date occurTime = warningInfo.getOccurTime();
				Date revertTime = warningInfo.getRevertTime();
				vo.setOccurTime(DateUtils.format(occurTime, DateUtils.DATE_TIME_PATTERN));
				if (null != revertTime) {
					vo.setRevertTime(DateUtils.format(revertTime, DateUtils.DATE_TIME_PATTERN));
					vo.setContinueTime(DateUtils.hmsBetween(occurTime, revertTime));
				} else {
					vo.setRevertTime("未恢复");
					vo.setContinueTime("-");
				}
				voList.add(vo);
			}
			result.setData(voList);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset <total){
			result.setHasNext(true);
		}
		result.setOffset(offset);
		result.setNextOffset(nextOffset);		
		result.setTotal(total);		
		return result;
	}
	
	/**
	 * 告警详情
	 */
	@Override
	public WarningVO getWarningDetail(Long id) {
		WarningVO vo = new WarningVO();
		if (null == id){
			return vo;
		}
		// 查询告警信息
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		WarningInfo warningInfo = companyWarningDao.queryObject(map);
		vo.setId(warningInfo.getId());
		vo.setType(WarningType.getByValue(warningInfo.getWarningType()).getDesc());
		if (warningInfo.getWarningType().equals(WarningType.GATEWAY_OFFLINE.getValue())) {
			vo.setObject(warningInfo.getWarningObject() + "号网关");
		} else { // 除设备掉线之外的告警类型
			String meterId = warningInfo.getWarningObject();
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("meterId", meterId);
			CompanyMeterInfo companyMeterInfo = companyMeterDao.queryObject(queryMap );
			if (null != companyMeterInfo) {
				vo.setObject(companyMeterInfo.getName());
			}
		}
		vo.setDescription(warningInfo.getDescription());
		vo.setDealStatus(warningInfo.getDealStatus());
		vo.setReadStatus(warningInfo.getReadStatus());
		Date occurTime = warningInfo.getOccurTime();
		Date revertTime = warningInfo.getRevertTime();
		vo.setOccurTime(DateUtils.format(occurTime, DateUtils.DATE_TIME_PATTERN));
		if (null != revertTime) {
			vo.setRevertTime(DateUtils.format(revertTime, DateUtils.DATE_TIME_PATTERN));
			vo.setContinueTime(DateUtils.hmsBetween(occurTime, revertTime));
		} else {
			vo.setRevertTime("未恢复");
			vo.setContinueTime("-");
		}
		// 告警readStatus更新，未读-->已读
		if (warningInfo.getReadStatus().equals(Constants.TAG_NO)) {
			warningInfo.setReadStatus(Constants.TAG_YES);
			companyWarningDao.update(warningInfo);
		}
		return vo;
	}
	
	
	/**
	 * 构建查询日期，精确到月份
	 * @param  date   yyyy-MM
	 * @return date   yyyyMM
	 */
	public static String buildSearchDateToMonth(String date){
		if (StringUtils.isNotBlank(date)) {
			String[] split = date.split("-");
			String yyyy = split[0];
			String MM = Integer.valueOf(split[1]) > 9 ? split[1] : ("0" + Integer.valueOf(split[1]));
			date = yyyy + MM;
		} else {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			date = month > 9 ? (String.valueOf(year) + String.valueOf(month)) : (year + "0" + month);
		}
		return date;
	}
	
	
	/**
	 * 构建查询日期，精确到日期
	 * @param date yyyy-MM-dd
	 * @return     yyyyMMdd
	 */
	public String buildSearchDateToDate(String date){
		String yyyyMMdd = "";
		if (StringUtils.isBlank(date)) {
			yyyyMMdd = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "");
		} else {
			yyyyMMdd = date.replace("-", "");
		}
		return yyyyMMdd;
	}
	

	/**
	 * 门户：智能监控>监控列表
	 * @param companyId  服务商的companyId
	 */
	@Override
	public PageResult<MonitorVO> getMonitorList(Long companyId, Integer limit, Integer offset) {
		PageResult<MonitorVO> result = new PageResult<>();
		if (null == companyId || null == limit || null == offset){
			return result;
		}
		// 获取该服务商的所有客户
		HashMap<String, Object> companyCustomerMap = new HashMap<>();	
		companyCustomerMap.put("companyId", companyId);
		List<CompanyCustomer> companyCustomerList = companyCustomerDao.getList(companyCustomerMap);
		if (CollectionUtils.isEmpty(companyCustomerList)) {
			return result;
		}
		List<Long> memberIdList = new ArrayList<>();
		for (CompanyCustomer companyCustomer : companyCustomerList) {
			memberIdList.add(companyCustomer.getMemberId());
		}
		if (CollectionUtils.isEmpty(memberIdList)) {
			return result;
		}
		// 根据客户memberId获取客户公司信息
		HashMap<String, Object> companyMap = new HashMap<>();
		companyMap.put("memberIdList", memberIdList);
		companyMap.put("limit", limit);
		companyMap.put("offset", offset);
		
		// 查询既设有主线也安装了监测点的公司信息
		int total = companyDao.getCompanyWithLineAndMeterCount(companyMap);
		int nextOffset = 0;
		if (total > 0) {
			// 获取当前日期
			Date now = new Date();
			String yyyyMMdd = DateUtils.format(now, DateUtils.DATE_PATTERN).replace("-", "");
			// 查询客户公司信息，并做分页处理
			List<Company> entityList = companyDao.getCompanyWithLineAndMeterList(companyMap);
			List<MonitorVO> monitorVOList = new ArrayList<>();
			for (Company company : entityList) {
				MonitorVO monitorVO = new MonitorVO();
				monitorVO.setCompanyId(company.getId());
				monitorVO.setCompanyName(company.getName());
				monitorVO.setTxImg(company.getTxImg());
				//获取企业总线信息及仪表编号，如果总线或仪表不存在，则直接遍历下一个公司
				CompanyLineInfo line = getCustomerMainLine(company.getId());
				if (null == line) {
					continue;
				}
				String meterId = line.getMeterNo();
				if (null == meterId) {
					continue;
				}
				// 查询公司当日综合用电情况
				DayAggregateReqData reqData = new DayAggregateReqData();
				reqData.setCompanyId(company.getId());
				reqData.setDate(yyyyMMdd);
				reqData.setMeterId(meterId);
				DayAggregateVO vo = reportSearchDubboService.getById(reqData);
				String electricQuantity = "0.00";
				String loadOfNow = "0.00";
				String maxLoadOfToday = "0.00";
				if (null != vo) {
					electricQuantity = BizUtils.doubleToString(vo.getElectric());
					loadOfNow = BizUtils.doubleToString(vo.getCurrentLoad());
					maxLoadOfToday = BizUtils.doubleToString(vo.getMaxLoad());
				}
				monitorVO.setElectricQuantity(electricQuantity);
				monitorVO.setLoadOfNow(loadOfNow);
				monitorVO.setMaxLoadOfToday(maxLoadOfToday);
				// 查询异常数量
				HashMap<String, Object> warningMap = new HashMap<>();
				warningMap.put("companyId", company.getId());
				int warningsNum = companyWarningDao.queryTotal(warningMap);
				monitorVO.setWarningsNum(warningsNum);
				
				monitorVOList.add(monitorVO);
			}
			result.setData(monitorVOList);
			nextOffset = offset + monitorVOList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}
	

	/**
	 * 实时负荷
	 * @param companyId  客户的companyId
	 * @param date 精确到天
	 */
	@Override
	public RealTimeLoadResult getRealTimeLoad(Long companyId, String date) {
		RealTimeLoadResult result = new RealTimeLoadResult();
		// 获取主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取今日和昨日日期 yyyyMMdd
		String today = buildSearchDateToDate(date);
		String yesterday = DateUtils.getYesterdayDate(today);
		if (null == today || null == yesterday) {
			result.set(ResultCode.ERROR_201, "日期转换异常");
			return result;
		}

		// 查询今日实时负荷
		DayLoadDetailListReqData reqData = new DayLoadDetailListReqData();
		reqData.setCompanyId(companyId);
		reqData.setMeterId(meterId);
		reqData.setPageSize(24 * 60 / period);
		reqData.addSort("time", SortOrder.ASC);
		reqData.setDate(today);
		ReportListResult<DayLoadDetailVO> todayResult = reportSearchDubboService.search(reqData);
		if (! todayResult.isSuccess()) {
			result.set(todayResult.getResultCode(), todayResult.getResultMessage());
			return result;
		}
		List<DayLoadDetailVO> todayLoadDetailVOList = todayResult.getList();
		// 构建今日实时负荷
		List<RealTimeLoadVO> loadsOfToday = buildRealLoad(todayLoadDetailVOList);
		// 今日最高负荷
		String maxLoadOfToday = "-";
		if (CollectionUtils.isNotEmpty(todayLoadDetailVOList)) {
			Optional<DayLoadDetailVO> opt = todayLoadDetailVOList.stream().max(Comparator.comparing(DayLoadDetailVO::getLoad));
			maxLoadOfToday = BizUtils.doubleToString(opt.get().getLoad());
		}
		
		// 查询昨日实时负荷，仅日期不同，其他查询数据不变
		reqData.setDate(yesterday);
		ReportListResult<DayLoadDetailVO> lastDayResult = reportSearchDubboService.search(reqData);
		if (! lastDayResult.isSuccess()) {
			result.set(lastDayResult.getResultCode(), lastDayResult.getResultMessage());
			return result;
		}
		List<DayLoadDetailVO> lastDayLoadDetailVOList = lastDayResult.getList();
		// 构建昨日实时负荷
		List<RealTimeLoadVO> loadsOfLastDay = buildRealLoad(lastDayLoadDetailVOList);
		// 昨日最高负荷
		String maxLoadOfLastDay = "-";
		if (CollectionUtils.isNotEmpty(lastDayLoadDetailVOList)) {
			Optional<DayLoadDetailVO> opt2 = lastDayLoadDetailVOList.stream().max(Comparator.comparing(DayLoadDetailVO::getLoad));
			maxLoadOfLastDay = BizUtils.doubleToString(opt2.get().getLoad());
		}
		
		result.setMaxLoadOfToday(maxLoadOfToday);
		result.setMaxLoadOfLastDay(maxLoadOfLastDay);
		result.setLoadsOfToday(loadsOfToday);
		result.setLoadsOfLastDay(loadsOfLastDay);
		return result;
	}

	/**
	 * 构建日实时负荷
	 * @param dayLoadDetailVOs
	 * @return
	 */
	public List<RealTimeLoadVO> buildRealLoad(List<DayLoadDetailVO> dayLoadDetailVOs){
		List<RealTimeLoadVO> realTimeLoadVOs = new ArrayList<>();
		Map<String, String> voMap = new HashMap<>();
		for (DayLoadDetailVO vo : dayLoadDetailVOs) {
			voMap.put(vo.getTime(), BizUtils.doubleToString(vo.getLoad()));
		}
		// 以00:00:00为起点构建时间节点集合
		List<String> timeList = BizUtils.timeListFormat("00:00:00", period, DateUtils.HHmmss);
		for (String time : timeList) {
			RealTimeLoadVO realTimeLoadVO = new RealTimeLoadVO(time, null);
			if (voMap.containsKey(time)) {
				realTimeLoadVO.setValue(voMap.get(time));;
			} else {
				realTimeLoadVO.setValue("-");
			}
			realTimeLoadVOs.add(realTimeLoadVO);
		}
		return realTimeLoadVOs;
	}

	/**
	 * 门户：智能监控>监控列表>总览>（综合、动力、照明、空调、特殊）
	 * @param companyId  客户的companyId
	 * @param date 精确到天
	 */
	@Override
	public PageResult<CollectorPointVO> getGeneralByType(Long companyId, Integer type, String date, Integer limit,
			Integer offset) {
		PageResult<CollectorPointVO> result = new PageResult<>();
		if (null == companyId || null == type || null == limit || null == offset){
			return result;
		}
		// 构建查询时间 yyyyMMdd
		String yyyyMMdd = buildSearchDateToDate(date);
		// 构建查询设备类型，如果查询综合，type为null
		type = type == EquipmentType.INTEGRATION.getValue() ? null : type;
		// 按条件查询客户仪表列表，带分页
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("equipmentType", type);
		map.put("limit", limit);
		map.put("offset", offset);
		int total = companyMeterDao.queryTotal(map);
		int nextOffset = 0;
		if (total > 0) {
			DayAggregateReqData reqData = new DayAggregateReqData();
			reqData.setCompanyId(companyId);
			reqData.setDate(yyyyMMdd);
			List<CollectorPointVO> collectorPoints = new ArrayList<>();
			List<CompanyMeterInfo> entityList = companyMeterDao.queryList(map);
			for (CompanyMeterInfo companyMeterInfo : entityList) {
				CollectorPointVO collectorPointVO = new CollectorPointVO();
				collectorPointVO.setId(companyMeterInfo.getId());
				collectorPointVO.setName(companyMeterInfo.getName());
				collectorPointVO.setAddress(companyMeterInfo.getEquipmentAddress());
				// 遍历查询每个采集点监测数据，仅仪表编号不同，其他查询数据不变
				reqData.setMeterId(companyMeterInfo.getMeterId());
				DayAggregateVO dayAggregateVO = reportSearchDubboService.getById(reqData);
				String maxLoadOfToday = "0.00";
				String quantityOfToday = "0.00";
				if (null != dayAggregateVO) {
					maxLoadOfToday = BizUtils.doubleToString(dayAggregateVO.getMaxLoad());
					quantityOfToday = BizUtils.doubleToString(dayAggregateVO.getElectric());
				}
				collectorPointVO.setMaxLoadOfToday(maxLoadOfToday);
				collectorPointVO.setQuantityOfToday(quantityOfToday);
				collectorPoints.add(collectorPointVO);
			}
			result.setData(collectorPoints);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}

	/**
	 * 门户：智能监控>告警
	 * 获取服务商所有客户的告警列表
	 * @param companyId  服务商的companyId
	 */
	@Override
	public PageResult<WarningVO> getAllWarnings(Long companyId, Integer limit, Integer offset) {
		PageResult<WarningVO> result = new PageResult<>();
		if (null == companyId || null == limit || null == offset){
			return result;
		}
		// 获取该服务商的所有客户
		HashMap<String, Object> companyCustomerMap = new HashMap<>();	
		companyCustomerMap.put("companyId", companyId);
		List<CompanyCustomer> companyCustomerList = companyCustomerDao.getList(companyCustomerMap);
		if (CollectionUtils.isEmpty(companyCustomerList)) {
			return result;
		}
		// 获取客户memberIdList
		List<Long> memberIdList = new ArrayList<>();
		for (CompanyCustomer companyCustomer : companyCustomerList) {
			memberIdList.add(companyCustomer.getMemberId());
		}
		if (CollectionUtils.isEmpty(memberIdList)) {
			return result;
		}
		// 获取客户公司信息
		HashMap<String, Object> companyMap = new HashMap<>();
		companyMap.put("memberIdList", memberIdList);
		List<Company> companyList = companyDao.getCompanyList(companyMap);
		if (CollectionUtils.isEmpty(companyList)) {
			return result;
		}
		// 获取客户companyId
		List<Long> companyIdList = new ArrayList<>();
		for (Company company : companyList) {
			companyIdList.add(company.getId());
		}
		if (CollectionUtils.isEmpty(companyIdList)) {
			return result;
		}
		
		// 构建告警查询条件
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyIdList", companyIdList);
		queryMap.put("limit", limit);
		queryMap.put("offset", offset);
		
		int total = companyWarningDao.queryTotal(queryMap);
		int nextOffset = 0;
		if (total > 0) {
			List<WarningInfo> entityList = companyWarningDao.queryList(queryMap);
			List<WarningVO> warningVOs = new ArrayList<>();
			for (WarningInfo warningInfo : entityList) {
				WarningVO warningVO = new WarningVO();
				Company company = companyDao.getCompanyById(warningInfo.getCompanyId());
				warningVO.setCompanyId(warningInfo.getCompanyId());
				warningVO.setCompanyName(company.getName());
				warningVO.setId(warningInfo.getId());
				warningVO.setType(WarningType.getByValue(warningInfo.getWarningType()).getDesc());
				if (warningInfo.getWarningType().equals(WarningType.GATEWAY_OFFLINE.getValue())) {
					warningVO.setObject(warningInfo.getWarningObject() + "号网关");
				} else { // 除设备掉线之外的告警类型
					String meterId = warningInfo.getWarningObject();
					Map<String, Object> queryMeterMap = new HashMap<>();
					queryMeterMap.put("meterId", meterId);
					CompanyMeterInfo companyMeterInfo = companyMeterDao.queryObject(queryMeterMap);
					if (null != companyMeterInfo) {
						warningVO.setObject(companyMeterInfo.getName());
					}
				}
				Date occurTime = warningInfo.getOccurTime();
				Date revertTime = warningInfo.getRevertTime();
				warningVO.setOccurTime(DateUtils.format(occurTime, DateUtils.DATE_TIME_PATTERN));
				if (null != revertTime) {
					warningVO.setRevertTime(DateUtils.format(revertTime, DateUtils.DATE_TIME_PATTERN));
					warningVO.setContinueTime(DateUtils.hmsBetween(occurTime, revertTime));
				} else {
					warningVO.setRevertTime("未恢复");
					warningVO.setContinueTime("-");
				}
				warningVO.setDescription(warningInfo.getDescription());
				warningVO.setDealStatus(warningInfo.getDealStatus());
				
				warningVOs.add(warningVO);
			}
			result.setData(warningVOs);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>用能排行
	 * @param companyId  客户公司ID
	 * @param date       精确到月份
	 * @return
	 */
	@Override
	public PageResult<BuildingVO> getConsumeRank(Long companyId, String date, Integer limit, Integer offset) {
		PageResult<BuildingVO> result = new PageResult<>();
		if (null == companyId || null == limit || null == offset){
			return result;
		}
		// 查询月份，本月和上月yyyyMM
		String thisMonth = buildSearchDateToMonth(date);
		String lastMonth = DateUtils.getLastMonth(thisMonth);
		// 楼宇
		Integer[] meterTypes = {2};
		
		// 本月用电
		MonthElectricListReqData req = new MonthElectricListReqData();
		req.setCompanyId(companyId);
		req.setMeterTypes(meterTypes);
		req.setOffset(offset);
		req.setPageSize(limit);
		req.addSort("total", SortOrder.DESC);
		req.setDate(thisMonth);
		ReportListResult<MonthElectricVO> thisMonthResult = reportSearchDubboService.search(req);
		if (! thisMonthResult.isSuccess()) {
			return result;
		}
		int total = thisMonthResult.getTotalCount();
		int nextOffset = 0;
		if (total > 0) {
			List<MonthElectricVO> entityList = thisMonthResult.getList();
			List<BuildingVO> buildingVOs = new ArrayList<>();
			// 上月用电的查询条件
			MonthElectricReqData reqData = new MonthElectricReqData();
			reqData.setCompanyId(companyId);
			reqData.setDate(lastMonth);
			for (int i = 0; i < entityList.size(); i++) {
				MonthElectricVO thisElectricVO = entityList.get(i);
				String quantityOfThisMonth = BizUtils.doubleToString(thisElectricVO.getTotal());
				String meterId = thisElectricVO.getMeterId();
				// 通过仪表编号获取楼宇信息
				Map<String, Object> map = new HashMap<>();
				map.put("meterId", meterId);
				CompanyMeterInfo meterInfo = companyMeterDao.queryObject(map);
				// 查询上月用电  仅meterId不同
				reqData.setMeterId(meterId);
				MonthElectricVO lastElectricVO = reportSearchDubboService.getById(reqData);
				// 构建 BuildingVO
				BuildingVO buildingVO = new BuildingVO();
				buildingVO.setQuantityOfThisMonth(quantityOfThisMonth);
				buildingVO.setRank(offset + (i + 1));
				if (null != meterInfo) {
					buildingVO.setId(meterInfo.getBuildingId());
					buildingVO.setName(meterInfo.getBuildingName());
				}
				if (null != lastElectricVO) {
					buildingVO.setQuantityOfLastMonth(BizUtils.doubleToString(lastElectricVO.getTotal()));
				}
				buildingVOs.add(buildingVO);
			}
			result.setData(buildingVOs);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}

	/**
	 * 门户：智能监控>监控列表>电压健康
	 * @param companyId  客户companyId
	 * @return
	 */
	@Override
	public SafetyVoltageResult getSafetyVoltage(Long companyId) {
		SafetyVoltageResult result = new SafetyVoltageResult();
		// 获取主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, null);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取查询日期   yyyyMMdd
		Date date = new Date();
		String yyyyMMdd = DateUtils.format(date, DateUtils.DATE_PATTERN);
		yyyyMMdd = yyyyMMdd.replace("-", "");
		// 获取查询电压合格率查询时间区间   start:本月第一天00:00:00      end:当前时间
		String yyyyMM = yyyyMMdd.substring(0, 6);
		Long start =DateUtils.parse(yyyyMM, "yyyyMM").getTime();
		Long end = date.getTime();
		
		DayVoltageCurrentDetailListReqData reqData = new DayVoltageCurrentDetailListReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(yyyyMMdd);
		reqData.setMeterId(meterId);
		reqData.setPageSize(24 * 60 / period);
		reqData.addSort("time", SortOrder.ASC);
		
		ReportListResult<DayVoltageCurrentDetailVO> search = reportSearchDubboService.search(reqData);
		if (! search.isSuccess()) {
			result.set(search.getResultCode(), search.getResultMessage());
			return result;
		}
		// 获取uas ubs ucs
		List<com.edianniu.pscp.cs.bean.power.vo.VoltageVO> uas = new ArrayList<>();
		List<com.edianniu.pscp.cs.bean.power.vo.VoltageVO> ubs = new ArrayList<>();
		List<com.edianniu.pscp.cs.bean.power.vo.VoltageVO> ucs = new ArrayList<>();
		List<DayVoltageCurrentDetailVO> voltageCurrentDetailVOList = search.getList();
		Map<String, DayVoltageCurrentDetailVO> voMap = new HashMap<>();
		for (DayVoltageCurrentDetailVO vo : voltageCurrentDetailVOList) {
			String time = vo.getTime();
			voMap.put(time, vo);
		}
		// 以00:00:00为起点，构建时间节点集合
		List<String> timeListFormat = BizUtils.timeListFormat("00:00:00", period, DateUtils.HHmmss);
		for (String time : timeListFormat) {
			com.edianniu.pscp.cs.bean.power.vo.VoltageVO ua = new com.edianniu.pscp.cs.bean.power.vo.VoltageVO(time, null);
			com.edianniu.pscp.cs.bean.power.vo.VoltageVO ub = new com.edianniu.pscp.cs.bean.power.vo.VoltageVO(time, null);
			com.edianniu.pscp.cs.bean.power.vo.VoltageVO uc = new com.edianniu.pscp.cs.bean.power.vo.VoltageVO(time, null);
			if (voMap.containsKey(time)) {
				ua.setValue(BizUtils.doubleToString(voMap.get(time).getUa()));
				ub.setValue(BizUtils.doubleToString(voMap.get(time).getUb()));
				uc.setValue(BizUtils.doubleToString(voMap.get(time).getUc()));
			} else {
				ua.setValue("-");
				ub.setValue("-");
				uc.setValue("-");
			}
			uas.add(ua);
			ubs.add(ub);
			ucs.add(uc);
		}
		// 获取此时的ua ub uc
		String uaOfNow = "0.00";
		String ubOfNow = "0.00";
		String ucOfNow = "0.00";
		if (CollectionUtils.isNotEmpty(voltageCurrentDetailVOList)) {
			DayVoltageCurrentDetailVO lastVO = voltageCurrentDetailVOList.get(voltageCurrentDetailVOList.size() - 1);
			uaOfNow = BizUtils.doubleToString(lastVO.getUa());
			ubOfNow = BizUtils.doubleToString(lastVO.getUb());
			ucOfNow = BizUtils.doubleToString(lastVO.getUc());
		}
		
		result.setUaOfNow(uaOfNow);
		result.setUbOfNow(ubOfNow);
		result.setUcOfNow(ucOfNow);
		result.setUas(uas);
		result.setUbs(ubs);
		result.setUcs(ucs);											 
		// 电压合格率   										
		Double rate = reportSearchDubboService.getVoltageQualifiedRate(companyId, meterId,        start,        end);
		result.setRateOfQualified(BizUtils.doubleToString(rate));    
		
		// 电压等级配置  up上限   1     down下限   2
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyId", companyId);
		queryMap.put("type", PowerConfigType.VOLTAGE.getValue());
		List<PowerOtherConfigInfo> configList = powerOtherConfigDao.queryConfigs(queryMap);
		if (CollectionUtils.isEmpty(configList)) {
			result.set(ResultCode.ERROR_201, "客户未配置电压等级");
			return result;
		}
		for (PowerOtherConfigInfo config : configList) {
			if (config.getKey() == 1) {
				result.setUpLimit(config.getValue());
			}
			if (config.getKey() == 2) {
				result.setDownLimit(config.getValue());
			}
		}
		return result;
	}

	/**
	 * 判断线路是否存在
	 * companyId  客户公司ID
	 * lineId     客户线路ID
	 */
	@Override
	public Boolean isExistLine(Long companyId, Long lineId) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("lineId", lineId);
		int total = companyLineDao.queryTotal(map);
		return total > 0 ? true : false;
	}
	
	/**
	 * 判断仪表是否存在
	 * @param companyId   客户公司ID
	 * @param meterId	     仪表编号
	 * @return
	 */
	@Override
	public Boolean isExistMeter(Long companyId, String meterId){
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("meterId", meterId);
		int total = companyMeterDao.queryTotal(map);
		return total > 0 ? true : false;
	}

	/**
	 * 门户：智能监控>监控列表>电流平衡
	 * companyId  客户公司ID
	 * lineId     客户线路ID
	 */
	@Override
	public CurrentBalanceResult getCurrentBalance(Long companyId, Long lineId) {
		CurrentBalanceResult result = new CurrentBalanceResult();
		// 获取仪表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, lineId);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取查询日期   yyyyMMdd
		Date date = new Date();
		String yyyyMMdd = DateUtils.format(date, DateUtils.DATE_PATTERN);
		yyyyMMdd = yyyyMMdd.replace("-", "");
		
		// 查询电流信息
		DayVoltageCurrentDetailListReqData reqData = new DayVoltageCurrentDetailListReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(yyyyMMdd);
		reqData.setMeterId(meterId);
		reqData.setPageSize(24 * 60 / period);
		reqData.addSort("time", SortOrder.ASC);
		ReportListResult<DayVoltageCurrentDetailVO> search = reportSearchDubboService.search(reqData);
		if (! search.isSuccess()) {
			result.set(search.getResultCode(), search.getResultMessage());
			return result;
		}
		
		// 获取unbalanceDegrees
		List<UnbalanceDegree> unbalanceDegrees = new ArrayList<>();
		List<DayVoltageCurrentDetailVO> currentVOList = search.getList();
		Map<String, DayVoltageCurrentDetailVO> voMap = new HashMap<>();
		for (DayVoltageCurrentDetailVO vo : currentVOList) {
			String time = vo.getTime();
			voMap.put(time, vo);
		}
		// 以00:00:00为时间起点获取时间节点集合
		List<String> timeList = BizUtils.timeListFormat("00:00:00", period, DateUtils.HHmmss);
		for (String time : timeList) {
			UnbalanceDegree unbalanceDegree = null;
			if (voMap.containsKey(time)) {
				DayVoltageCurrentDetailVO vo = voMap.get(time);
				String u = BizUtils.doubleToString(vo.getiUnbalanceDegree());
				String ia = BizUtils.doubleToString(vo.getIa());
				String ib = BizUtils.doubleToString(vo.getIb());
				String ic = BizUtils.doubleToString(vo.getIc());
				unbalanceDegree = new UnbalanceDegree(time, u, ia, ib, ic);
			} else {
				unbalanceDegree = new UnbalanceDegree(time, "-", "-", "-", "-");
			}
			unbalanceDegrees.add(unbalanceDegree);
		}
		
		// 获取此时的a相、b相、c相电流及不平衡度
		String aPhase = "0.00";
		String bPhase = "0.00";
		String cPhase = "0.00";
		String unbalanceDegreeOfNow = "0.00";
		if (CollectionUtils.isNotEmpty(currentVOList)) {
			DayVoltageCurrentDetailVO lastVO = currentVOList.get(currentVOList.size() - 1);
			aPhase = BizUtils.doubleToString(lastVO.getIa());
			bPhase = BizUtils.doubleToString(lastVO.getIb());
			cPhase = BizUtils.doubleToString(lastVO.getIc());
			unbalanceDegreeOfNow = BizUtils.doubleToString(lastVO.getiUnbalanceDegree());
		}
		
		result.setLineId((Long)map.get("lineId"));
		result.setLineName((String)map.get("lineName"));
		result.setaPhase(aPhase);
		result.setbPhase(bPhase);
		result.setcPhase(cPhase);
		result.setUnbalanceDegreeOfNow(unbalanceDegreeOfNow);
		result.setUnbalanceDegrees(unbalanceDegrees);
		int compareTo = Double.valueOf(unbalanceDegreeOfNow).compareTo(currentUnbalanceStandard);
		if (compareTo <= 0) { // 如果不平衡度大于10%(0.1)则电流不平衡
			result.setIsBalance(Constants.TAG_YES);
		} else {
			result.setIsBalance(Constants.TAG_NO);
		}
		
		// 查询当日综合数据-->分别获取A相、B相和C相失平衡点信息
		DayAggregateListReqData req = new DayAggregateListReqData();
		req.setCompanyId(companyId);
		req.setDate(yyyyMMdd);
		req.setPageSize(200);
		ReportListResult<DayAggregateVO> search2 = reportSearchDubboService.search(req);
		if (! search2.isSuccess()) {
			result.set(search2.getResultCode(), search2.getResultMessage());
			return result;
		}
		List<Map<String, Object>> aUnbalancePointList = new ArrayList<>();
		List<Map<String, Object>> bUnbalancePointList = new ArrayList<>();
		List<Map<String, Object>> cUnbalancePointList = new ArrayList<>();
		List<DayAggregateVO> dayAggregateVOList = search2.getList();
		for (DayAggregateVO dayAggregateVO : dayAggregateVOList) {
			Double iaUnbalanceDegree = dayAggregateVO.getIaUnbalanceDegree();
			Double ibUnbalanceDegree = dayAggregateVO.getIbUnbalanceDegree();
			Double icUnbalanceDegree = dayAggregateVO.getIcUnbalanceDegree();
			int compareOfA = iaUnbalanceDegree.compareTo(currentUnbalanceStandard);
			int compareOfB = ibUnbalanceDegree.compareTo(currentUnbalanceStandard);
			int compareOfC = icUnbalanceDegree.compareTo(currentUnbalanceStandard);
			if (compareOfA > 0 || compareOfB > 0 || compareOfC > 0) { // 如果不平衡度大于10%(0.1)则电流不平衡
				HashMap<String, Object> queryMeterMap = new HashMap<>();
				queryMeterMap.put("meterId", dayAggregateVO.getMeterId());
				CompanyMeterInfo meterInfo = companyMeterDao.queryObject(queryMeterMap);
				if (null == meterInfo) {
					continue;
				}
				Map<String, Object> pointMap = new HashMap<>();
				pointMap.put("lineId", meterInfo.getLineId());
				pointMap.put("meterName", meterInfo.getName());
				if (compareOfA > 0) { // A相失衡点
					aUnbalancePointList.add(pointMap);
				}
				if (compareOfB > 0) { // B相失衡点
					bUnbalancePointList.add(pointMap);
				}
				if (compareOfC > 0) { // C相失衡点
					cUnbalancePointList.add(pointMap);
				}
			}
		}
		result.setaUnbalancePointList(aUnbalancePointList);
		result.setbUnbalancePointList(bUnbalancePointList);
		result.setcUnbalancePointList(cUnbalancePointList);
		
		return result;
	}

	/**
	 * 门户：智能监控>监控列表>峰谷利用
	 * companyId  客户公司ID
	 */
	@Override
	public UseFengGUResult useFengGU(Long companyId, Long lineId) {
		UseFengGUResult result = new UseFengGUResult();
		// 获取主表meterId
		DefaultResult defaultResult = getCompanyMainMeterId(companyId, lineId);
		if (! defaultResult.isSuccess()) {
			result.set(defaultResult.getResultCode(), defaultResult.getResultMessage());
			return result;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) defaultResult.getObject();
		String meterId = (String) map.get("meterId");
		// 获取当前日期
		Date now = new Date();
		String yyyyMMdd = DateUtils.format(now, DateUtils.DATE_PATTERN).replace("-", "");
		
		// 查询用电详情
		DayDetailLogListReqData reqData = new DayDetailLogListReqData ();
		reqData.setCompanyId(companyId);
		reqData.setDate(yyyyMMdd);
		reqData.setMeterId(meterId);
		reqData.setPageSize(24 * 60 / period);
		reqData.addSort("startTime", SortOrder.ASC);
		ReportListResult<DayDetailLogVO> reportListResult = reportSearchDubboService.search(reqData);
		if (! reportListResult.isSuccess()) {
			result.set(reportListResult.getResultCode(), reportListResult.getResultMessage());
			return result;
		}
		
		// 获取日用电信息
		List<QuantityVO> quantities = new ArrayList<>();
		List<DayDetailLogVO> dayElectricChargeDetailVOs = reportListResult.getList();
		Map<String, DayDetailLogVO> voMap = new HashMap<>();
		for (DayDetailLogVO vo : dayElectricChargeDetailVOs) {
			String startTime = DateUtils.timeStampFormatStr(vo.getStartTime(), DateUtils.HHmm);
			String endTime = DateUtils.timeStampFormatStr(vo.getEndTime(), DateUtils.HHmm);
			String timeSpace = startTime + "-" + endTime;
			voMap.put(timeSpace, vo);
		}
		
		// 以00:00:00为时间起点，构建时间节点集合
		List<String> timeListFormat = BizUtils.timeListFormat("00:00", period, DateUtils.HHmm);
		// 构建时间段集合
		List<String> timeSpaceList = new ArrayList<>();
		for (String startTime : timeListFormat) {
			String endTime = DateUtils.strAddMinutes(startTime, period, DateUtils.HHmm);
			String timeSpace = startTime + "-" + endTime;
			timeSpaceList.add(timeSpace);
		}			
		for (String timeSpace : timeSpaceList) {
			QuantityVO quantity = null;
			if (voMap.containsKey(timeSpace)) {
				DayDetailLogVO vo = voMap.get(timeSpace);
				quantity = new QuantityVO(timeSpace, BizUtils.doubleToString(vo.getElectric()), BizUtils.doubleToString(vo.getFee()), vo.getChargeType());
			} else {
				quantity = new QuantityVO(timeSpace, "0.00", "0.00", 0);
			}
			quantities.add(quantity);
		}
		result.setQuantities(quantities);
		
		// 监测点用电的监测时间段（即当前时间周期的上一个时间周期）
		String period = null;
		String HHmm = DateUtils.format(now, DateUtils.HHmm); 
		for (int i = 0; i < timeSpaceList.size(); i++) {
			String timeSpace = timeSpaceList.get(i);
			String[] split = timeSpace.split("-");
			String endTime = split[1];
			int compareTo = endTime.compareTo(HHmm);
			if (compareTo > 0) {
				period = timeSpaceList.get(i - 1);
				break;
			}
		}
		result.setPeriod(period);
		
		// 查询当日总电量、总电价、谷电量和谷电价
		DayElectricChargeReqData reqData2 = new DayElectricChargeReqData();
		reqData2.setCompanyId(companyId);
		reqData2.setDate(yyyyMMdd);
		reqData2.setMeterId(meterId);
		DayElectricChargeVO dayElectricChargeVO = reportSearchDubboService.getById(reqData2);
		String quantityOfToday = "0.00";
		String quantityOfGu = "0.00";
		String chargeOfToday = "0.00";
		String chargeOfGu = "0.00";
		if (null != dayElectricChargeVO) {
			quantityOfToday = BizUtils.doubleToString(dayElectricChargeVO.getTotal());
			quantityOfGu = BizUtils.doubleToString(dayElectricChargeVO.getValley());
			chargeOfToday = BizUtils.doubleToString(dayElectricChargeVO.getTotalCharge());
			chargeOfGu = BizUtils.doubleToString(dayElectricChargeVO.getValleyCharge());
		}
		result.setQuantityOfToday(quantityOfToday);
		result.setQuantityOfGu(quantityOfGu);
		result.setChargeOfToday(chargeOfToday);
		result.setChargeOfGu(chargeOfGu);
		
		/*尖峰平谷时间分布及电费单价*/
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyId", companyId);
		PowerPriceConfigInfo config = powerPriceConfigDao.queryObject(queryMap);
		if (null == config) {
			result.set(ResultCode.ERROR_201, "客户未配置电费参数");
			return result;
		}
		// 电度电费计费模式：  1分时       0普通
		List<DistributeOfTime> distributeOfTimes = new ArrayList<>();
		if (config.getChargeMode() == ChargeModeType.NORMAL.getValue()) {
			// 如果为普通计费方式，则一律视为平时段
			Double basePrice = config.getBasePrice();
			String baseTimeInterval = "[\"00:00-24:00\"]";
			List<DistributeOfTime> pingList = buildTimeTypes(baseTimeInterval, TimeType.PING.getDesc(), BizUtils.doubleToString(basePrice));
			distributeOfTimes.addAll(pingList);
		} else {
			// jian
			Double apexPrice = config.getApexPrice();
			String apexTimeInterval = config.getApexTimeInterval();
			List<DistributeOfTime> jinaList = buildTimeTypes(apexTimeInterval, TimeType.JIAN.getDesc(), BizUtils.doubleToString(apexPrice));
			// feng
			Double peakPrice = config.getPeakPrice();
			String peakTimeInterval = config.getPeakTimeInterval();
			List<DistributeOfTime> fengList = buildTimeTypes(peakTimeInterval, TimeType.FENG.getDesc(), BizUtils.doubleToString(peakPrice));
			// ping
			Double flatPrice = config.getFlatPrice();
			String flatTimeInterval = config.getFlatTimeInterval();
			List<DistributeOfTime> pingList = buildTimeTypes(flatTimeInterval, TimeType.PING.getDesc(), BizUtils.doubleToString(flatPrice));
			// gu
			Double valleyPrice = config.getValleyPrice();
			String valleyTimeInterval = config.getValleyTimeInterval();
			List<DistributeOfTime> guList = buildTimeTypes(valleyTimeInterval, TimeType.GU.getDesc(), BizUtils.doubleToString(valleyPrice));
			
			distributeOfTimes.addAll(jinaList);
			distributeOfTimes.addAll(fengList);
			distributeOfTimes.addAll(pingList);
			distributeOfTimes.addAll(guList);
		}
		// 时间升序排序
		Collections.sort(distributeOfTimes);
		result.setDistributeOfTimes(distributeOfTimes);
		
		return result;
	}
	
	
	/**
	 * 构建尖峰平谷时间分布及电费单价
	 * @param timeInterval   ["08:00-11:00","15:00-19:00"]
	 * @param type           JIAN FENG PING GU
	 * @param price          单价
	 * @return
	 */
	public List<DistributeOfTime> buildTimeTypes(String timeInterval, String type, String price){
		List<DistributeOfTime> distributeOfTimes = new ArrayList<>();
		JSONArray timeSpaceArray = JSON.parseArray(timeInterval);
		for (Object object : timeSpaceArray) {
			String timeSpace = (String) object;
			String start = timeSpace.split("-")[0].substring(0, 2);
			String end = timeSpace.split("-")[1].substring(0, 2);
			for (int i = Integer.valueOf(start); i < Integer.valueOf(end); i++) {
				for (int j = 0; j < 4; j++) {
					DistributeOfTime distributeOfTime = new DistributeOfTime();
					String hour = i < 10 ? ("0" + i) : ("" + i);
					String minute = j == 0 ? ("00") : ("" + j * period);
					String time = hour +  ":" + minute + ":00";
					distributeOfTime.setTime(time);
					distributeOfTime.setType(type);
					distributeOfTime.setPrice(price);
					distributeOfTimes.add(distributeOfTime);
				}
			}
		}
		return distributeOfTimes;
	}
	
	
	/**
	 * 分页获取监测点数据
	 * 门户：功率因数---获取监测点功率因数 
	 */
	@Override
	public PageResult<CollectorPointVO> getCollectorPointsForPowerFactor(Long companyId, Integer limit, Integer offset) {
		PageResult<CollectorPointVO> result = new PageResult<>();
		if (null == companyId) {
			return result;
		}
		// 获取当前日期
		String yyyyMMdd = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "");

		// 监测点功率因数
		List<CollectorPointVO> collectorPoints = new ArrayList<>();
		DayPowerFactorReqData reqData = new DayPowerFactorReqData();
		reqData.setCompanyId(companyId);
		reqData.setDate(yyyyMMdd);
		
		// 查询客户所有的监测点
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("limit", limit);
		map.put("offset", offset);
		int total = companyMeterDao.queryTotal(map);
		int nextOffset = 0;
		if (total > 0) {
			List<CompanyMeterInfo> entityList = companyMeterDao.queryList(map);
			for (CompanyMeterInfo meter : entityList) {
				CollectorPointVO collectorPointVO = new CollectorPointVO();
				// 功率因数   每次仅仪表编号不同，其他查询信息不变
				reqData.setMeterId(meter.getMeterId());
				
				
				DayPowerFactorVO dayPowerFactorVO = reportSearchDubboService.getById(reqData);
				String powerFactor = "0.00";
				if (null != dayPowerFactorVO) {
					powerFactor = BizUtils.doubleToString(dayPowerFactorVO.getFactor());
				}
				collectorPointVO.setId(meter.getId());
				collectorPointVO.setName(meter.getName());
				collectorPointVO.setPowerFactor(powerFactor);
				collectorPointVO.setAddress(meter.getEquipmentAddress());
				collectorPointVO.setType(meter.getEquipmentType());
				EquipmentType equipmentType = EquipmentType.getByValue(meter.getEquipmentType());
				String equipmentTypeName = equipmentType == null ? null :equipmentType.getDesc();
				collectorPointVO.setTypeName(equipmentTypeName);
				collectorPoints.add(collectorPointVO);
			}
			result.setData(collectorPoints);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}
	
	
	/**
	 * 分页获取监测点数据
	 * 门户：峰谷利用---获取监测点在监测时段用电量、电费
	 * @param startTime HH:mm
	 * @param endTime HH:mm
	 */
	@Override
	public PageResult<CollectorPointVO> getCollectorPointsForPowerQuantity(Long companyId, Integer limit, Integer offset, String startTime, String endTime) {
		PageResult<CollectorPointVO> result = new PageResult<>();
		if (null == companyId) {
			return result;
		}
		// 获取当前日期yyyy-MM-dd并拼接日期和时间
		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		startTime = date + " " + startTime + ":00";
		endTime = date + " " + endTime + ":00";
		// 将拼接好的时间转为时间戳，为防止延时而读不到数据，故end时间可略微推迟，此处推迟1分钟
		Long start = DateUtils.strFormatTimeStamp(startTime, DateUtils.DATE_TIME_PATTERN);
		Long end = DateUtils.strFormatTimeStamp(endTime, DateUtils.DATE_TIME_PATTERN) + (1 * 60 * 1000L);
		
		// 楼宇、设备
		Integer[] meterTypes = {2, 3};

		// 监测点用电和电费
		List<CollectorPointVO> collectorPoints = new ArrayList<>();
		DayDetailLogListReqData reqData = new DayDetailLogListReqData();
		reqData.setCompanyId(companyId);
		reqData.setMeterTypes(meterTypes);
		reqData.setStartTime(start);
		reqData.setEndTime(end);
		reqData.setOffset(offset);
		reqData.setPageSize(limit);
		reqData.addSort("electric", SortOrder.DESC);
		ReportListResult<DayDetailLogVO> reportListResult = reportSearchDubboService.search(reqData);
		if (!reportListResult.isSuccess()) {
			return result;
		}
		
		int total = reportListResult.getTotalCount();
		int nextOffset = 0;
		if (total > 0) {
			List<DayDetailLogVO> entityList = reportListResult.getList();
			for (DayDetailLogVO dayVO : entityList) {
				CollectorPointVO collectorPointVO = new CollectorPointVO();
				String charge = BizUtils.doubleToString(dayVO.getFee());
				String quantity = BizUtils.doubleToString(dayVO.getElectric());
				collectorPointVO.setCharge(charge);
				collectorPointVO.setQuantity(quantity);
				// 根据meterId获取监测点信息
				String meterId = dayVO.getMeterId();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("meterId", meterId);
				CompanyMeterInfo meterInfo = companyMeterDao.queryObject(map);
				if (null != meterInfo) {
					collectorPointVO.setId(meterInfo.getId());
					collectorPointVO.setName(meterInfo.getName());
					collectorPointVO.setLineId(meterInfo.getLineId());
					collectorPointVO.setAddress(meterInfo.getEquipmentAddress());
				}
				collectorPoints.add(collectorPointVO);
			}
			result.setData(collectorPoints);
			nextOffset = offset + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setOffset(offset);
		result.setTotal(total);
		return result;
	}
	
	/**
	 * 获取企业总线
	 * @param companyId 企业ID
	 * @return
	 */
	public CompanyLineInfo getCustomerMainLine(Long companyId){
		CompanyLineInfo line = new CompanyLineInfo(); 
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("parentId", 0);
		line = companyLineDao.queryObject(map);
		return line;
	}

	/**
	 * 判断仪表是否有数据
	 */
	@Override
	public HasDataResult hasData(String meterId, Long companyId) {
		HasDataResult result = new HasDataResult();
		VoltageListReqData reqData = new VoltageListReqData();
		reqData.setCompanyId(companyId);
		reqData.setMeterId(meterId);
		reqData.setPageSize(10);
		ReportListResult<VoltageVO> reportListResult = reportSearchDubboService.search(reqData);
		if (! reportListResult.isSuccess()) {
			result.set(reportListResult.getResultCode(), reportListResult.getResultMessage());
			return result; 
		}
		List<VoltageVO> voltageVOList = reportListResult.getList();
		if (CollectionUtils.isNotEmpty(voltageVOList)) {
			result.setHasData(Constants.TAG_YES);
		} else {
			result.setHasData(Constants.TAG_NO);
		}
		return result;
	}

	
}
