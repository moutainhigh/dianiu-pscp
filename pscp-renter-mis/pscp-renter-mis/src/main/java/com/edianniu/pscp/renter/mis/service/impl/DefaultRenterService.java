package com.edianniu.pscp.renter.mis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.edianniu.pscp.cs.bean.power.EquipmentType;
import com.edianniu.pscp.renter.mis.bean.renter.*;
import com.edianniu.pscp.renter.mis.bean.renter.vo.*;
import com.edianniu.pscp.renter.mis.commons.Constants;
import com.edianniu.pscp.renter.mis.commons.PageResult;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.dao.CompanyMeterDao;
import com.edianniu.pscp.renter.mis.dao.PowerPriceConfigDao;
import com.edianniu.pscp.renter.mis.dao.RenterChargeOrderDao;
import com.edianniu.pscp.renter.mis.dao.RenterConfigDao;
import com.edianniu.pscp.renter.mis.dao.RenterDao;
import com.edianniu.pscp.renter.mis.dao.RenterMeterDao;
import com.edianniu.pscp.renter.mis.domain.CompanyMeter;
import com.edianniu.pscp.renter.mis.domain.PowerPriceConfig;
import com.edianniu.pscp.renter.mis.domain.Renter;
import com.edianniu.pscp.renter.mis.domain.RenterConfig;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;
import com.edianniu.pscp.renter.mis.service.RenterService;
import com.edianniu.pscp.renter.mis.util.BizUtils;
import com.edianniu.pscp.renter.mis.util.DateUtils;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.AvgListResult;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;

import stc.skymobi.cache.redis.JedisUtil;

@Service
@Repository("renterService")
@Transactional
public class DefaultRenterService implements RenterService{

	@Autowired
	@Qualifier("renterDao")
	private RenterDao renterDao;
	
	@Autowired
	@Qualifier("renterConfigDao")
	private RenterConfigDao renterConfigDao;
	
	@Autowired
	@Qualifier("renterMeterDao")
	private RenterMeterDao renterMeterDao;
	
	@Autowired
	@Qualifier("renterChargeOrderDao")
	private RenterChargeOrderDao renterChargeOrderDao;
	
	@Autowired
	@Qualifier("companyMeterDao")
	private CompanyMeterDao companyMeterDao;
	
	@Autowired
	@Qualifier("powerPriceConfigDao")
	private PowerPriceConfigDao powerPriceConfigDao;
	
	@Autowired
	@Qualifier("reportSearchDubboService")
	private ReportSearchDubboService reportSearchDubboService;
	
	@Autowired
	@Qualifier("jedisUtil")
	private JedisUtil jedisUtil;
	

	@Value(value = "${sample.count}")
	private int samples = 10;
	
	@Override
	public List<RenterVO> getList(Long uid) {
		List<Renter> renters = renterDao.getListByMemberId(uid);
		List<RenterVO> renterVOs = new ArrayList<>();
		for (Renter renter : renters) {
			RenterVO vo = new RenterVO();
			BeanUtils.copyProperties(renter, vo);
			vo.setRenterId(renter.getId());
			renterVOs.add(vo);
		}
		return renterVOs;
	}

	@Override
	public PageResult<RenterVO> getList(HashMap<String, Object> queryMap) {
		PageResult<RenterVO> result = new PageResult<>();
		
		int total = renterDao.queryTotal(queryMap);
		int nextOffset = 0;
		if (total > 0) {
			List<Renter> renterList = renterDao.queryList(queryMap);
			List<RenterVO> renterVOList = new ArrayList<>();
			for (Renter renter : renterList) {
				RenterVO renterVO = new RenterVO();
				BeanUtils.copyProperties(renter, renterVO);
				renterVO.setRenterId(renter.getId());
				renterVO.setCreateTime(DateUtils.format(renter.getCreateTime()));
				renterVO.setBalance(BizUtils.doubleToString(renter.getBalance()));
				
				// 未缴单数和未缴单数（月结用户）
				if (null != renter.getChargeMode()) {
					if (renter.getChargeMode().equals(ChargeModeType.MONTH_SETTLE.getValue())) {
						NopayInfo nopayInfo = renterChargeOrderDao.getNopayInfo(renter.getId());
						if (null != nopayInfo) {
							renterVO.setNopayCount(nopayInfo.getNopayCount());
							renterVO.setNopayCharge(BizUtils.doubleToString(nopayInfo.getNopayCharge()));
						}
					}
				}
				
				// 监测点个数
				Map<String, Object> map = new HashMap<>();
				map.put("renterId", renter.getId());
				int count = renterMeterDao.queryTotal(map);
				renterVO.setPointCount(count);
				
				/*// 判断缓存
				Set<String> members = jedisUtil.smembers(CacheKey.CACHE_KEY_NETDAU_CONTROL_SWITCH + renter.getId());
				if (!CollectionUtils.isEmpty(members)) {
					String firstMember = members.iterator().next();
					int length = firstMember.length();
					if (length > 3) {
						String code = firstMember.substring(length - 3, length);
						if (code.equals("001")) {//合闸
							renterVO.setSwitchStatus(3); 
						} else if (code.equals("002")) {//开闸
							renterVO.setSwitchStatus(2);
						}
					}
				}*/
				renterVOList.add(renterVO);
			}
			result.setData(renterVOList);
			nextOffset = renterList.size() + (Integer)queryMap.get("offset");
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}
	
	/**
	 * 租客首页
	 */
	@Override
	public HomeResult home(Long renterId, Long companyId, String balance) {
		HomeResult result = new HomeResult();
		Map<String, Object> map = new HashMap<>();
		map.put("renterId", renterId);
		List<RenterMeter> rmList = renterMeterDao.queryList(map);
		RenterConfig renterConfig = renterConfigDao.queryObject(map);
		if (null == renterConfig) {
			result.set(ResultCode.RENTER_CONFIG_ERROR, "租客电费参数尚未配置");
			return result;
		}
		Set<String> meterNos = new HashSet<>();        //仪表编号集合
		Map<String, Double> rateMap = new HashMap<>(); //费用占比
		for (RenterMeter renterMeter : rmList) {
			meterNos.add(renterMeter.getMeterNo());
			rateMap.put(renterMeter.getMeterNo(), renterMeter.getRate());
		}
		
		ElectricChargeStatReqData reqData = new ElectricChargeStatReqData();
		reqData.setMeterIds(meterNos);
		reqData.setCompanyId(companyId);
		reqData.setType("MONTH");
		reqData.setSource("DAY_LOG");
		Map<String, Date> searchMonth = buildSearchTimeSpace(companyId, renterConfig, null);
		if (null == searchMonth) {
			result.set(ResultCode.ERROR_201, "查询日期构建失败");
			return result;
		}
		Date smallDate = searchMonth.get("smallDate");
		Date bigDate = searchMonth.get("bigDate");
		if (null == smallDate || null == bigDate) {
			result.set(ResultCode.ERROR_201, "查询日期构建失败");
			return result;
		}
		reqData.setFromDate(DateUtils.format(smallDate).replace("-", ""));
		reqData.setToDate(DateUtils.format(bigDate).replace("-", ""));
		
		StatListResult<ElectricChargeStat> statListResult = reportSearchDubboService.getElectricChargeStats(reqData);
		if (!statListResult.isSuccess()) {
			result.set(statListResult.getResultCode(), statListResult.getResultMessage());
			return result;
		}
		
		/*分项用电*/
		double totalCharge = 0.0;
		double totalQuantity = 0.0;
		List<UseType> useTypes = new ArrayList<>();
		double quantityOfPower = 0.0;
		double quantityOfLighting = 0.0;
		double quantityOfAirConditioner = 0.0;
		double quantityOfSpecial = 0.0;
		double chargeOfPower = 0.0;
		double chargeOfLighting = 0.0;
		double chargeOfAirConditioner = 0.0;
		double chargeOfSpecial = 0.0;
		List<ElectricChargeStat> list = statListResult.getList();
		for (ElectricChargeStat electric : list) {
			double rate = rateMap.get(electric.getMeterId()) / 100;//费用占比
			double quantityOfThisMeter = electric.getTotalElectric() * rate;
			double chargeOfthisMeter = getChargeOfThisMeter(rate, renterConfig, electric);
			totalQuantity = totalQuantity + quantityOfThisMeter;
			totalCharge = totalCharge + chargeOfthisMeter;
			
			// 分项用电数据   
			switch (electric.getSubTermCode()) {
			case "01C00":   //动力
				quantityOfPower += quantityOfThisMeter;
				chargeOfPower += chargeOfthisMeter;
				break; 
			case "01A00":   //照明
				quantityOfLighting += quantityOfThisMeter;
				chargeOfLighting += chargeOfthisMeter;
				break; 
			case "01B00":   //空调
				quantityOfAirConditioner += quantityOfThisMeter;
				chargeOfAirConditioner += chargeOfthisMeter;
				break; 
			default:        //特殊
				quantityOfSpecial += quantityOfThisMeter;
				chargeOfSpecial += chargeOfthisMeter;
				break; 
			}
		}
		UseType ut1 = new UseType(null, EquipmentType.POWER.getValue(), EquipmentType.POWER.getDesc(), null, null, null, 
				BizUtils.doubleToString(quantityOfPower), BizUtils.doubleToString(chargeOfPower));
		UseType ut2 = new UseType(null, EquipmentType.LIGHTING.getValue(), EquipmentType.LIGHTING.getDesc(), null, null, null, 
				BizUtils.doubleToString(quantityOfLighting), BizUtils.doubleToString(chargeOfLighting));
		UseType ut3 = new UseType(null, EquipmentType.AIR_CONDITIONER.getValue(), EquipmentType.AIR_CONDITIONER.getDesc(), null, null, null, 
				BizUtils.doubleToString(quantityOfAirConditioner), BizUtils.doubleToString(chargeOfAirConditioner));
		UseType ut4 = new UseType(null, EquipmentType.SPECIAL.getValue(), EquipmentType.SPECIAL.getDesc(), null, null, null, 
				BizUtils.doubleToString(quantityOfSpecial), BizUtils.doubleToString(chargeOfSpecial));
		useTypes.add(ut1);
		useTypes.add(ut2);
		useTypes.add(ut3);
		useTypes.add(ut4);
		
		result.setCharges(useTypes);
		result.setTotalCharge(BizUtils.doubleToString(totalCharge));
		result.setChargeMode(renterConfig.getChargeMode());
		
		// 剩余天数
		if (renterConfig.getChargeMode().equals(ChargeModeType.PAY_FIRST.getValue())) {
			result = getLeftDays(renterConfig, companyId, meterNos, result, rateMap, balance);
		}
		return result;
	}
	
	/**
	 * 剩余天数
	 */
	public HomeResult getLeftDays(RenterConfig config, Long companyId, Set<String> meterNos,
			HomeResult result, Map<String, Double> rateMap, String balance){
		// 查询开始时间，应保证至少有10天的数据积累
		Date startTime = config.getStartTime();
		Date beforeTenDays = DateUtils.dateAddDays(new Date(), -10);
		if (beforeTenDays.before(startTime)) {
			result.setDays(null);
			return result;
		}
		AvgOfMetreReqData req = new AvgOfMetreReqData();
		req.setCompanyId(companyId);
		req.setFromDate(DateUtils.format(startTime).replace("-", ""));
		req.setMeterIds(meterNos);
		AvgListResult<AvgOfMeterStat> avgOfMeterResult = reportSearchDubboService.avgOfMeter(req);
		if (!avgOfMeterResult.isSuccess()) {
			result.set(avgOfMeterResult.getResultCode(), avgOfMeterResult.getResultMessage());
			return result;
		}
		List<AvgOfMeterStat> list = avgOfMeterResult.getList();
		double totalChargePerDay = 0.0;
		for (AvgOfMeterStat avgOfMeterStat : list) {
			double rate = rateMap.get(avgOfMeterStat.getMeterId()) / 100;//费用占比
			double chargeOfthisMeter = getChargeOfThisMeter(rate, config, avgOfMeterStat);
			totalChargePerDay += chargeOfthisMeter;
		}
		//计算剩余天数
		if (!BizUtils.isPositiveNumber(balance)) {
			result.setDays(null);
		} else if (totalChargePerDay <= 0) {
			result.setDays(null);
		} else {
			int days = (int) (Double.valueOf(balance) / totalChargePerDay);
			result.setDays(days);
		}
		return result;
	}
	
	

	/**
	 * 剩余天数
	 */
	public HomeResult getLeftDays(RenterConfig config, ElectricChargeStatReqData reqData, 
			HomeResult result, Map<String, Double> rateMap, String balance){
		//1.获取查询时间段
		Date today = new Date();
		int year = DateUtils.getYear(today);
		int month = DateUtils.getMonth(today);
		int date = DateUtils.getDate(today);
		Date t1 = DateUtils.buildDate(year, month, date-1-samples);
		Date t2 = DateUtils.buildDate(year, month, date-1);
		// 剔除节假日....
		Date startTime = config.getStartTime();
		if (t1.before(startTime)) {
			result.setDays(null);
			return result;
		}
		//2.查询
		reqData.setType("DAY");
		reqData.setFromDate(DateUtils.format(t1).replace("-", ""));
		reqData.setToDate(DateUtils.format(t2).replace("-", ""));
		StatListResult<ElectricChargeStat> statListResult = reportSearchDubboService.getElectricChargeStats(reqData);
		if (!statListResult.isSuccess()) {
			result.set(statListResult.getResultCode(), statListResult.getResultMessage());
			return result;
		}
		List<ElectricChargeStat> list = statListResult.getList();
		double totalCharge = 0.0;
		for (ElectricChargeStat electric : list) {
			double rate = rateMap.get(electric.getMeterId()) / 100;//费用占比
			double chargeOfthisMeter = getChargeOfThisMeter(rate, config, electric);
			totalCharge = totalCharge + chargeOfthisMeter;
		}
		//3.计算剩余天数
		if (!BizUtils.isPositiveNumber(balance)) {
			result.setDays(null);
		} else if (totalCharge == 0) {
			result.setDays(null);
		} else if (samples == 0) {
			result.setDays(null);
		} else {
			double chargePerDay = totalCharge / samples;
			int days = (int) (Double.valueOf(balance) / chargePerDay);
			result.setDays(days);
		}
		return result;
	}
	
	/**
	 * 构建分项用电数据
	 * @param ut
	 * @param electric
	 * @return
	 */
	@Override
	public UseType buildUserType(UseType ut, String subTermCode){
		switch (subTermCode) {
		case "01C00": ut.setTTQC(EquipmentType.POWER.getValue(), EquipmentType.POWER.getDesc());        //动力
			break; 
		case "01A00": ut.setTTQC(EquipmentType.LIGHTING.getValue(), EquipmentType.LIGHTING.getDesc());  //照明
			break; 
		case "01B00": ut.setTTQC(EquipmentType.AIR_CONDITIONER.getValue(), EquipmentType.AIR_CONDITIONER.getDesc()); //空调
			break; 
		default:ut.setTTQC(EquipmentType.SPECIAL.getValue(), EquipmentType.SPECIAL.getDesc()); //特殊
			break; 
		}
		return ut;
	}
	
	/**
	 * 对象归并
	 */
	@Override
	public List<UseType> mergeUseTypes(List<UseType> useTypes) {
		Map<String, UseType> map = new HashMap<>();
		for (UseType useType : useTypes) {
			String meterNo = useType.getMeterNo();
			if (map.containsKey(meterNo)) {
				useType.setCharge(BizUtils.stringAddString(useType.getCharge(), map.get(meterNo).getCharge()));
				useType.setQuantity(BizUtils.stringAddString(useType.getQuantity(), map.get(meterNo).getQuantity()));
			}
			map.put(meterNo, useType);
		}
		useTypes.clear();
		useTypes.addAll(map.values());
		return useTypes;
	}
	
	/**
	 * 计算仪表电费
	 * @param rate
	 * @param renterConfig
	 * @param electric
	 * @return
	 */
	@Override
	public double getChargeOfThisMeter(double rate, RenterConfig renterConfig, ElectricChargeStat electric){
		double chargeOfthisMeter = 0.0; 
		if (renterConfig.getSubChargeMode().equals(RenterConfig.NORMAL_CHARGE_MODE)) {
			chargeOfthisMeter = 
					rate * electric.getTotalElectric() * renterConfig.getBasePrice();
		} else {
			chargeOfthisMeter = rate * (
					electric.getApexElectric() * renterConfig.getApexPrice() +
					electric.getPeakElectric() * renterConfig.getPeakPrice() +
					electric.getFlatElectric() * renterConfig.getFlatPrice() +
					electric.getValleyElectric() * renterConfig.getValleyPrice());
		}
		return chargeOfthisMeter;
	}
	
	/**
	 * 计算仪表电费
	 * @param rate
	 * @param config
	 * @param avgOfMeterStat
	 */
	private double getChargeOfThisMeter(double rate, RenterConfig config, AvgOfMeterStat avgOfMeterStat) {
		double chargeOfthisMeter = 0.0;
		if (config.getSubChargeMode().equals(RenterConfig.NORMAL_CHARGE_MODE)) {
			chargeOfthisMeter = 
					rate * avgOfMeterStat.getAvgOfTotal() * config.getBasePrice();
		} else {
			chargeOfthisMeter = rate * (
					avgOfMeterStat.getAvgOfApex() * config.getApexPrice() +
					avgOfMeterStat.getAvgOfPeak() * config.getPeakPrice() +
					avgOfMeterStat.getAvgOfFlat() * config.getFlatPrice() +
					avgOfMeterStat.getAvgOfValley() * config.getValleyPrice());
		}
		return chargeOfthisMeter;
	}
	
	/**
	 * 构建查询时间范围
	 * @param companyId
	 * @param renterConfig
	 * @param time
	 * @return
	 */
	@Override
	public Map<String, Date> buildSearchTimeSpace(Long companyId, RenterConfig renterConfig, String time){
		// 获取规定账单日期
		PowerPriceConfig config = powerPriceConfigDao.queryObject(companyId);
		int countDate = Integer.valueOf(config.getChargeTimeInterval());
		// 确定查询年月日（假设某租客固定账单日为每月15日，则其4月份的查询上下限为：3月15日--4月14日）
		int monthOfSearch1 = 0;
		int monthOfSearch2 = 0;
		int yearOfSearch1  = 0;
		int yearOfSearch2  = 0;
		int dateOfSearch1  = countDate;
		int dateOfSearch2  = countDate - 1;
		
		Date today = new Date();
		if (StringUtils.isBlank(time)) {//未指定查询时间时，默认按月查询(本月或者上月)，根据今日日期与规定账单日期的关系而定
			// 获取今日日期，比较今日日期和规定账单日期
			
			yearOfSearch1 = DateUtils.getYear(today);
			yearOfSearch2 = DateUtils.getYear(today);
			if (DateUtils.getDate(today) < countDate) { //如果今日日期小于规定结算日期，则查询上月电费
				monthOfSearch1 = DateUtils.getMonth(today) - 2;
				monthOfSearch2 = DateUtils.getMonth(today) - 1;
			} else {                                    //否则查询本月电费
				monthOfSearch1 = DateUtils.getMonth(today) - 1;
				monthOfSearch2 = DateUtils.getMonth(today);
			}
		} else if (time.length() == 6) {//指定查询月份时
			yearOfSearch1  = Integer.valueOf(time.substring(0, 4));
			yearOfSearch2  = Integer.valueOf(time.substring(0, 4));
			monthOfSearch1 = Integer.valueOf(time.substring(4, 6)) - 1;
			monthOfSearch2 = Integer.valueOf(time.substring(4, 6));
			
			/* test代码  by zjj 20180516 */
			int month = 0;
			if (DateUtils.getDate(today) < countDate) { //最新有数据的一个月份
				month = DateUtils.getMonth(today);
			} else {
				month = DateUtils.getMonth(today) + 1;
			}
			if (monthOfSearch2 == month) { //如果查询月份为最新有数据一个月份时
				monthOfSearch2 = DateUtils.getMonth(today);
				dateOfSearch2  = DateUtils.getDate(today);
			}
			
		} else if (time.length() == 4) {//指定查询年份时
			yearOfSearch1 = Integer.valueOf(time) - 1;
			yearOfSearch2 = Integer.valueOf(time);
			monthOfSearch1 = 12;
			monthOfSearch2 = 12;
		} else if (time.length() == 8){//指定查询日期时
			yearOfSearch1  = Integer.valueOf(time.substring(0, 4));
			yearOfSearch2  = Integer.valueOf(time.substring(0, 4));
			monthOfSearch1 = Integer.valueOf(time.substring(4, 6));
			monthOfSearch2 = Integer.valueOf(time.substring(4, 6));
			dateOfSearch1  = Integer.valueOf(time.substring(6, 8));
			dateOfSearch2  = Integer.valueOf(time.substring(6, 8));
		} else {
			return null;
		}
		// 1.一般处理，确定查询日期上限和下限  
		Date smallDate = DateUtils.buildDate(yearOfSearch1, monthOfSearch1, dateOfSearch1);   //上限
		Date bigDate = DateUtils.buildDate(yearOfSearch2, monthOfSearch2, dateOfSearch2);     //下限
  		// 2.比较smallDate与startTime，如果startTime比smallDate大，说明是第一个计费周期，应用startTime取代smallDate
		Date startTime = renterConfig.getStartTime();
		smallDate = startTime.after(smallDate) ? startTime : smallDate;
		// 3.比较bigDate与endTime，如果endTime比bigDate小，说明是最后一个计费周期，应用endTime取代bigDate；如果endTime为空，则什么也不做
		Date endTime = renterConfig.getEndTime();
		if (null != endTime) {
			bigDate = endTime.before(bigDate) ? endTime : bigDate;
		}
		Map<String, Date> result = new HashMap<>();
		result.put("smallDate", smallDate);
		result.put("bigDate", bigDate);
		return result;
	}

	/**
	 * 租客用电数据列表
	 */
	@Override
	public PageResult<RenterDataVO> getDataList(HashMap<String, Object> queryMap, Long companyId, String time) {
		PageResult<RenterDataVO> result = new PageResult<>();
		
		int total = renterDao.queryTotal(queryMap);
		int nextOffset = 0;
		if (total > 0) {
			List<Renter> renterList = renterDao.queryList(queryMap);
			List<RenterDataVO> dataList = new ArrayList<>();
			for (Renter renter : renterList) {
				RenterDataVO dataVO = new RenterDataVO();
				dataVO.setRenterId(renter.getId());
				dataVO.setName(renter.getName());
				dataVO.setAddress(renter.getAddress());
				Map<String, Object> map = new HashMap<>();
				map.put("renterId", renter.getId());
				List<RenterMeter> rmList = renterMeterDao.queryList(map);
				RenterConfig renterConfig = renterConfigDao.queryObject(map);
				if (null == renterConfig) {
					continue;
				}
				Set<String> meterNos = new HashSet<>();       // 仪表编号集合
				Map<String, Double> rateMap = new HashMap<>();// 费用占比
				for (RenterMeter renterMeter : rmList) {
					meterNos.add(renterMeter.getMeterNo());
					rateMap.put(renterMeter.getMeterNo(), renterMeter.getRate());
				}
				
				// 查询仪表数据
				double charge = 0.0;
				double quantity = 0.0;
				ElectricChargeStatReqData reqData = new ElectricChargeStatReqData();
				reqData.setMeterIds(meterNos);
				reqData.setCompanyId((Long)queryMap.get("companyId"));
				reqData.setSource("DAY_LOG");
				if (null == time || time.length() == 6) {
					reqData.setType("MONTH");
				} else if (time.length() == 4) {
					reqData.setType("YEAR");
				} else {
					reqData.setType("DAY");
				}
				Date smallDate = null;
				Date bigDate = null;
				Map<String, Date> searchTimeSpace = buildSearchTimeSpace(companyId, renterConfig, time);
				if (null == searchTimeSpace) {
					continue;
				} else {
					smallDate = searchTimeSpace.get("smallDate");
					bigDate = searchTimeSpace.get("bigDate");
					if (null == smallDate || null == bigDate) {
						continue;
					} else {
						Date startTime = renterConfig.getStartTime();
						Date endTime = renterConfig.getEndTime();
						// 如果上限时间比关系解除时间还晚、或者下限时间比开始合作时间还早，则不调用查询接口
						if ((null != endTime && smallDate.after(endTime)) || bigDate.before(startTime)) {
							continue;
						}
					}
				}
				reqData.setFromDate(DateUtils.format(smallDate).replace("-", ""));
				reqData.setToDate(DateUtils.format(bigDate).replace("-", ""));
				StatListResult<ElectricChargeStat> statListResult = reportSearchDubboService.getElectricChargeStats(reqData);
				if (statListResult.isSuccess()) {
					List<ElectricChargeStat> list = statListResult.getList();
					for (ElectricChargeStat electric : list) {
						Double rate = rateMap.get(electric.getMeterId()) / 100;//费用占比
						double quantityOfThisMeter = electric.getTotalElectric() * rate;
						double chargeOfThisMeter = getChargeOfThisMeter(rate, renterConfig, electric);
						quantity = quantity + quantityOfThisMeter;
						charge = charge + chargeOfThisMeter;
					}
				}
				dataVO.setCharge(BizUtils.doubleToString(charge));
				dataVO.setQuantity(BizUtils.doubleToString(quantity));
				dataVO.setTime(buildTime(time, smallDate, bigDate));
				
				dataList.add(dataVO);
			}
			result.setData(dataList);
			nextOffset = renterList.size() + (Integer)queryMap.get("offset");
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}
	
	/**
	 * 日期形式转换
	 * 年--------->yyyy
	 * 月--------->yyyy-MM - yyyy-MM
	 * 日--------->yyyy-MM-dd
	 */
	private String buildTime(String time, Date smallDate, Date bigDate) {
		String result = "";
		if (null == time || time.length() == 6) { //月
			String date1 = DateUtils.format(smallDate).substring(0, 7);//yyyy-MM
			String date2 = DateUtils.format(bigDate).substring(0, 7);  //yyyy-MM
			result = date1 + " - " + date2;
		} else if (time.length() == 8) { //日
			String yyyy = time.substring(0, 4);
			String MM = time.substring(4, 6);
			String dd = time.substring(6, 8);
			result = yyyy + "-" + MM + "-" + dd;
		} else if (time.length() == 4) {
			result = time;
		} else {
			result = "";
		}
		return result;
	}

	@Override
	public Long save(String name, String mobile, String contract, String address, Long renterMemberId, Long companyId) {
		Renter renter = new Renter();
		renter.setName(name == null ? null : name.trim());
		renter.setMobile(mobile == null ? null : mobile.trim());
		renter.setContract(contract == null ? null : contract.trim());
		renter.setAddress(address == null ? null : address.trim());
		renter.setMemberId(renterMemberId);
		renter.setCompanyId(companyId);
		renterDao.save(renter);
		Long renterId = renter.getId();
		return renterId;
	}

	@Override
	public void update(Long renterId, String name, String mobile, String contract, String address) {
		Renter renter = new Renter();
		renter.setId(renterId);
		renter.setName(name == null ? null : name.trim());
		renter.setMobile(mobile == null ? null : mobile.trim());
		renter.setContract(contract == null ? null : contract.trim());
		renter.setAddress(address == null ? null : address.trim());
		renterDao.update(renter);
	}

	@Override
	public RenterVO getById(Long id, Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("companyId", companyId);
		Renter renter = renterDao.getInfo(map);
		RenterVO renterVO = new RenterVO();
		if (null == renter) {
			return null;
		}
		BeanUtils.copyProperties(renter, renterVO);
		renterVO.setRenterId(renter.getId());
		renterVO.setCreateTime(DateUtils.format(renter.getCreateTime()));
		renterVO.setBalance(BizUtils.doubleToString(renter.getBalance()));
		return renterVO;
	}
	@Override
	public Renter getById(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		Renter renter = renterDao.getInfo(map);
		return renter;
	}
	@Override
	public Boolean isRenterNameExist(String name, Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("companyId", companyId);
		return renterDao.queryCount(map) > 0 ? true : false;
	}


	@Override
	public Boolean isRenterExit(String mobile, Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("companyId", companyId);
		return renterDao.queryCount(map) > 0 ? true : false;
	}


	@Override
	public Boolean isConfigExist(Long configId, Long renterId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", configId);
		map.put("renterId", renterId);
		return renterConfigDao.queryCount(map) > 0 ? true : false;
	}

	@Override
	public void saveConfig(SaveConfigReq req) {
		/* 创建租客配置信息 */
		RenterConfig config = new RenterConfig();
		config.setRenterId(req.getRenterId());
		config.setChargeMode(req.getChargeMode());
		config.setAmountLimit(StringUtils.isBlank(req.getAmountLimit()) ? 0.0D : Double.valueOf(req.getAmountLimit()));
		config.setStartTime(DateUtils.getStartDate(DateUtils.parse(req.getStartTime())));
		config.setEndTime(DateUtils.getStartDate(DateUtils.parse(req.getEndTime())));
		config.setNextBillCreateTime(req.getFirstOrderTime());
		config.setSwitchStatus(Constants.TAG_NO);
		config.setSubChargeMode(req.getSubChargeMode());
		config.setBasePrice(StringUtils.isBlank(req.getBasePrice()) ? 0.0D : Double.valueOf(req.getBasePrice()));
		config.setApexPrice(StringUtils.isBlank(req.getApexPrice()) ? 0.0D : Double.valueOf(req.getApexPrice()));
		config.setPeakPrice(StringUtils.isBlank(req.getPeakPrice()) ? 0.0D : Double.valueOf(req.getPeakPrice()));
		config.setFlatPrice(StringUtils.isBlank(req.getFlatPrice()) ? 0.0D : Double.valueOf(req.getFlatPrice()));
		config.setValleyPrice(StringUtils.isBlank(req.getValleyPrice()) ? 0.0D : Double.valueOf(req.getValleyPrice()));
		renterConfigDao.save(config);
		
		/* 创建租客仪表配置，批量插入租客仪表配置  */
		List<RenterMeterInfo> meterList = req.getMeterList();
		List<RenterMeter> renterMeterList = buildRenterList(meterList, req.getRenterId());
		renterMeterDao.saveBatch(renterMeterList);
	}
	
	@Override
	public void updateConfig(SaveConfigReq req) {
		/* 修改租客配置 */
		RenterConfig config = new RenterConfig();
		config.setId(req.getId());
		config.setChargeMode(req.getChargeMode());
		config.setAmountLimit(StringUtils.isBlank(req.getAmountLimit()) ? 0.0D : Double.valueOf(req.getAmountLimit()));
		config.setEndTime(DateUtils.getStartDate(DateUtils.parse(req.getEndTime())));
		config.setSubChargeMode(req.getSubChargeMode());
		config.setBasePrice(StringUtils.isBlank(req.getBasePrice()) ? 0.0D : Double.valueOf(req.getBasePrice()));
		config.setApexPrice(StringUtils.isBlank(req.getApexPrice()) ? 0.0D : Double.valueOf(req.getApexPrice()));
		config.setPeakPrice(StringUtils.isBlank(req.getPeakPrice()) ? 0.0D : Double.valueOf(req.getPeakPrice()));
		config.setFlatPrice(StringUtils.isBlank(req.getFlatPrice()) ? 0.0D : Double.valueOf(req.getFlatPrice()));
		config.setValleyPrice(StringUtils.isBlank(req.getValleyPrice()) ? 0.0D : Double.valueOf(req.getValleyPrice()));
		renterConfigDao.update(config);
		
		/* 修改租客仪表配置  */	
		// 批量删除之前存在的仪表配置
		RenterMeter rMeter = new RenterMeter();
		rMeter.setRenterId(req.getRenterId());
		rMeter.setDeleted(Constants.TAG_YES);
		renterMeterDao.update(rMeter);
		// 批量插入新的租客仪表配置
		List<RenterMeterInfo> meterList = req.getMeterList();
		List<RenterMeter> renterMeterList = buildRenterList(meterList, req.getRenterId());
		renterMeterDao.saveBatch(renterMeterList);
	}
	
	@Override
	public void updateConfig(Long id, Long renterId, Integer chargeMode, Integer switchStatus){
		RenterConfig config = new RenterConfig();
		config.setId(id);
		config.setRenterId(renterId);
		config.setChargeMode(chargeMode);
		config.setSwitchStatus(switchStatus);
		renterConfigDao.update(config);
	}
	
	/**
	 * 将meter集合构建为renterMeter集合，并验证费用占比情况
	 * @param meterList
	 * @param renterId
	 * @return
	 */
	public List<RenterMeter> buildRenterList(List<RenterMeterInfo> meterList, Long renterId){
		List<RenterMeter> renterMeterList = new ArrayList<>();
		for (RenterMeterInfo meter : meterList) {
			RenterMeter renterMeter = new RenterMeter();
			renterMeter.setRenterId(renterId);
			renterMeter.setBuildingId(meter.getBuildingId());
			renterMeter.setMeterId(meter.getId());
			renterMeter.setRate(meter.getRate());
			renterMeterList.add(renterMeter);
			// 验证仪表总费用占比情况
			checkAndUpdateMeterRate(meter.getId(), meter.getRate());
		}
		return renterMeterList;
	}
	
	/**
	 * 验证仪表总费用占比情况
	 * 1.检查仪表总费用占比
	 * 2.修改仪表费用占比系数
	 * @param meterId 仪表ID
	 * @param rate    当前租客所选择的仪表费用占比
	 */
	public void checkAndUpdateMeterRate(Long meterId, Double rate){
		Double rateSum = rateSum(meterId);
		Double newSum = rate + rateSum;
		CompanyMeter companyMeter = new CompanyMeter();
		companyMeter.setId(meterId);
		if (newSum >= 100) {
			companyMeter.setAllot(Constants.TAG_YES); //如果总占比到达100%，则系数更改为1
		} else {
			companyMeter.setAllot(Constants.TAG_NO); //如果总占比小于100%，则系数更改为0
		}
		companyMeterDao.update(companyMeter);
	}
	
	@Override
	public double rateSum(Long meterId){
		return renterMeterDao.rateSum(meterId);
	}
	
	@Override
	public double getRate(Long meterId, Long renterId) {
		Double rate = renterMeterDao.getRate(meterId, renterId);
		return rate == null ? 0.0 : rate;
	}

	@Override
	public RenterConfigVO getRenterConfigVO(Long renterId) {
		RenterConfigVO renterConfigVO = new RenterConfigVO();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("renterId", renterId);
		RenterConfig config = renterConfigDao.queryObject(map1);
		if (null != config) {
			BeanUtils.copyProperties(config, renterConfigVO);
			renterConfigVO.setStartTime(DateUtils.format(config.getStartTime()));
			renterConfigVO.setEndTime(DateUtils.format(config.getEndTime()));
		}
		
		List<RenterMeter> renterMeterList = renterMeterDao.queryList(map1);
		List<RenterMeterInfo> meterList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(renterMeterList)) {
			for (RenterMeter renterMeter : renterMeterList) {
				RenterMeterInfo meter = new RenterMeterInfo();
				BeanUtils.copyProperties(renterMeter, meter);
				meter.setId(renterMeter.getMeterId());
				
				meterList.add(meter);
			}
		}
		renterConfigVO.setMeterList(meterList);
		return renterConfigVO;
	}
	
	@Override
	public RenterConfig getRenterConfig(Long renterId) {
		Map<String, Object> params = new HashMap<>();
		params.put("renterId", renterId);
		RenterConfig config = renterConfigDao.queryObject(params);
		return config;
	}
	@Override
	public List<Long> getRenterIds(Integer chargeMode, Integer limit) {
		Map<String,Object> parmas=new HashMap<String,Object>();
		parmas.put("chargeMode", chargeMode);
		parmas.put("size", limit);
		return renterDao.getRenterIds(parmas);
	}

	@Override
	@Transactional
	public boolean updateConfigBillTaskStatus(Long id, Integer billTaskStatus) {
		int count=renterConfigDao.updateBillTaskStatus(id, billTaskStatus,"定时任务");
		return count>0?true:false;
	}

	@Override
	public boolean updateConfigBillTaskInfo(RenterConfig renterConfig) {
		int count=renterConfigDao.updateBillTaskInfo(renterConfig);
		return count>0?true:false;
	}

	

	

}
