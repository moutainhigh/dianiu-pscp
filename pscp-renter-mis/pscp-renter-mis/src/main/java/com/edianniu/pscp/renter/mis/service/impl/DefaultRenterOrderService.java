package com.edianniu.pscp.renter.mis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.power.ChargeType;
import com.edianniu.pscp.renter.mis.domain.MeterInfo;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.renter.mis.bean.renter.ChargeModeType;
import com.edianniu.pscp.renter.mis.bean.renter.CountItem;
import com.edianniu.pscp.renter.mis.bean.renter.OrderDetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.OrderListResult;
import com.edianniu.pscp.renter.mis.bean.renter.UseType;
import com.edianniu.pscp.renter.mis.bean.renter.vo.OrderVO;
import com.edianniu.pscp.renter.mis.commons.Constants;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.dao.MeterInfoDao;
import com.edianniu.pscp.renter.mis.dao.PowerPriceConfigDao;
import com.edianniu.pscp.renter.mis.dao.RenterChargeOrderDao;
import com.edianniu.pscp.renter.mis.dao.RenterConfigDao;
import com.edianniu.pscp.renter.mis.dao.RenterDao;
import com.edianniu.pscp.renter.mis.dao.RenterMeterDao;
import com.edianniu.pscp.renter.mis.domain.PowerPriceConfig;
import com.edianniu.pscp.renter.mis.domain.Renter;
import com.edianniu.pscp.renter.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.renter.mis.domain.RenterConfig;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;
import com.edianniu.pscp.renter.mis.service.RenterOrderService;
import com.edianniu.pscp.renter.mis.service.RenterService;
import com.edianniu.pscp.renter.mis.util.BizUtils;
import com.edianniu.pscp.renter.mis.util.DateUtils;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;

@Service
@Transactional
@Repository("renterOrderService")
public class DefaultRenterOrderService implements RenterOrderService {
	
	@Autowired
	@Qualifier("renterDao")
	private RenterDao renterDao;
	@Autowired
	@Qualifier("renterConfigDao")
	private RenterConfigDao renterConfigDao;
	@Autowired
	@Qualifier("meterInfoDao")
	private MeterInfoDao meterInfoDao;
	@Autowired
	@Qualifier("renterMeterDao")
	private RenterMeterDao renterMeterDao;
	
	@Autowired
	@Qualifier("renterChargeOrderDao")
	private RenterChargeOrderDao renterChargeOrderDao;
	
	@Autowired
	@Qualifier("powerPriceConfigDao")
	private PowerPriceConfigDao powerPriceConfigDao;

	@Autowired
	@Qualifier("reportSearchDubboService")
	private ReportSearchDubboService reportSearchDubboService;
	
	@Autowired
	private RenterService renterService;
	
	/**
	 * 租客账单列表
	 */
	@Override
	public OrderListResult getOrderList(Map<String, Object> map, Long companyId) {
		OrderListResult result = new OrderListResult();
		// 当前用量
		String quantityOfNow = "0.00";
		// 账单列表数据
		int total = renterChargeOrderDao.queryTotal(map);
		int nextOffset = 0;
		List<OrderVO> orderVOs = new ArrayList<>();
		
		if (total > 0) {
			List<RenterChargeOrder> list = renterChargeOrderDao.queryList(map);
			for (RenterChargeOrder order : list) {
				OrderVO orderVO = transToOrderVO(order);
				orderVOs.add(orderVO);
			}
			nextOffset = list.size() + (Integer)map.get("offset");
			
			RenterChargeOrder firstOrder = list.get(0);  //获取最近一个月账单
			quantityOfNow = BizUtils.doubleToString(firstOrder.getQuantity());
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setBillList(orderVOs);
		result.setNextOffset(nextOffset);
		result.setTotalCount(total);
		result.setQuantityOfNow(quantityOfNow);
		// 结算日
		result.setCountDate(buildCountDate(companyId));
		
		return result;
	}

	/**
	 * 结算日
	 * @param companyId 业主公司ID（指的是客户）
	 */
	private String buildCountDate(Long companyId) {
		String result = "";
		PowerPriceConfig powerPriceConfig = powerPriceConfigDao.queryObject(companyId);
		if (null != powerPriceConfig) {
			result = powerPriceConfig.getChargeTimeInterval();
		}
		return result;
	}

	/**
	 * @param  RenterChargeOrder
	 * @return OrderVO
	 */
	private OrderVO transToOrderVO(RenterChargeOrder order) {
		OrderVO orderVO = new OrderVO();
		BeanUtils.copyProperties(order, orderVO);
		orderVO.setPeriod(DateUtils.format(order.getLastCheckDate()) 
				+ " - " + DateUtils.format(order.getThisCheckDate()));//电费周期
		orderVO.setThisCheckDate(DateUtils.format(order.getThisCheckDate()));//本次抄表日期
		orderVO.setPayTime(DateUtils.format(order.getPayTime())); //缴费日期
		orderVO.setQuantity(BizUtils.doubleToString(order.getQuantity()));
		orderVO.setCharge(BizUtils.doubleToString(order.getCharge()));
		if (order.getPayStatus().equals(PayStatus.SUCCESS.getValue())) {//成功支付
			orderVO.setPayStatus(Constants.TAG_YES);//缴费状态0:（月结---未缴、预付—未结清）   1:（月结---已缴费、预付---已结清）
		}
		if (order.getChargeMode().equals(ChargeModeType.PAY_FIRST.getValue())) {// 预付
			if (!orderVO.getPayStatus().equals(Constants.TAG_YES)) {//未成功支付-->未结清
				orderVO.setNoPayCount(BizUtils.doubleToString(order.getCharge()-order.getPrepaidCharge()));//未结金额
			}
		}
		return orderVO;
	}

	/**
	 * 租客账单详情
	 * @return
	 */
	@Override
	public OrderDetailResult getOrderDetail(Long id) {
		OrderDetailResult result = new OrderDetailResult();
		// 账单详情
		RenterChargeOrder order = renterChargeOrderDao.queryObject(id);
		if (null == order) {
			result.set(ResultCode.ERROR_201, "账单ID不合法");
			return result;
		}
		OrderVO orderVO = transToOrderVO(order);
		result.setBill(orderVO);
		Renter renter = renterDao.queryObject(order.getRenterId());
		if (null == renter) {
			result.set(ResultCode.ERROR_201, "账单ID不合法");
			return result;
		}
		
		/*封装仪表信息：编号、倍率、线路名称*/
		Map<String, Object> map = new HashMap<>();
		map.put("renterId", order.getRenterId());
		List<RenterMeter> rmList = renterMeterDao.queryList(map);
		RenterConfig renterConfig = renterConfigDao.queryObject(map);
		Set<String> meterNos = new HashSet<>();               //仪表编号集合
		Map<String, Integer> multipleMap = new HashMap<>();   //倍率
		Map<String, String> lineNameMap = new HashMap<>();    //线路名称
		Map<String, Double> rateMap = new HashMap<>();        //费用占比
		for (RenterMeter renterMeter : rmList) {
			meterNos.add(renterMeter.getMeterNo());
			multipleMap.put(renterMeter.getMeterNo(), renterMeter.getMultiple());
			lineNameMap.put(renterMeter.getMeterNo(), renterMeter.getLineName());
			rateMap.put(renterMeter.getMeterNo(), renterMeter.getRate());
		}
		result.setSubChargeMode(renterConfig.getSubChargeMode());
		
		boolean isDividTimeUser = (renterConfig.getSubChargeMode().equals(com.edianniu.pscp.cs.bean.power.ChargeModeType.DIVIDE_TIME.getValue()));
		ElectricChargeStatReqData reqData = new ElectricChargeStatReqData();
		reqData.setMeterIds(meterNos);
		reqData.setCompanyId(order.getCompanyId());
		reqData.setType("MONTH");
		reqData.setSource("DAY_LOG");
		String searchMonth = DateUtils.format(order.getThisCheckDate(), DateUtils.yyyyMM);
		Map<String, Date> searchTimeSpace = renterService.buildSearchTimeSpace(renter.getCompanyId(), renterConfig, searchMonth);
		if (null == searchTimeSpace) {
			result.set(ResultCode.ERROR_201, "查询日期构建失败");
			return result;
		}
		Date smallDate = searchTimeSpace.get("smallDate");
		Date bigDate = searchTimeSpace.get("bigDate");
		if (null == smallDate || null == bigDate) {
			result.set(ResultCode.ERROR_201, "查询日期构建失败");
			return result;
		}
		reqData.setFromDate(DateUtils.format(smallDate, DateUtils.yyyyMMdd));
		reqData.setToDate(DateUtils.format(bigDate, DateUtils.yyyyMMdd));
		StatListResult<ElectricChargeStat> statListResult = reportSearchDubboService.getElectricChargeStats(reqData);
		if (!statListResult.isSuccess()) {
			result.set(statListResult.getResultCode(), statListResult.getResultMessage());
			return result;
		}
		
		//初始化分时电量
		double quantityJIAN = 0.0;
		double quantityFENG = 0.0;
		double quantityPING = 0.0;
		double quantityGU = 0.0;
		List<UseType> useTypes = new ArrayList<>(); //分项用电
		List<ElectricChargeStat> list = statListResult.getList();
		Set<String> actualFoundMeterNos = new HashSet<>();//实际可以查到数据的仪表 
		for (ElectricChargeStat electric : list) {
			actualFoundMeterNos.add(electric.getMeterId());
			double rate = rateMap.get(electric.getMeterId()) / 100;//费用占比
			// 分时电量统计
			if (isDividTimeUser) {
				quantityJIAN = quantityJIAN + electric.getApexElectric() * rate;
				quantityFENG = quantityFENG + electric.getPeakElectric() * rate;
				quantityPING = quantityPING + electric.getFlatElectric() * rate;
				quantityGU = quantityGU + electric.getValleyElectric() * rate;
			}
			
			// 分项用电数据
			double quantityOfThisMeter = electric.getTotalElectric() * rate;
			double chargeOfthisMeter = renterService.getChargeOfThisMeter(rate, renterConfig, electric);
			UseType ut = new UseType(electric.getMeterId(), null, null, 
					BizUtils.doubleToString(rate),
					lineNameMap.get(electric.getMeterId()), 
					multipleMap.get(electric.getMeterId()), 
					BizUtils.doubleToString(quantityOfThisMeter), 
					BizUtils.doubleToString(chargeOfthisMeter));
			useTypes.add(renterService.buildUserType(ut, electric.getSubTermCode()));
		}
		
		/**
		 * List<UseType>对象归并
		 */
		useTypes = renterService.mergeUseTypes(useTypes);
		
		/**
		 * 比较租客仪表集合与实际可以查到数据的仪表集合，获取差集 
		 * 15位 建筑编号(10)  网关编号(2) 仪表Id(3)
		 */
		List<String> notFoundMeterNos = new ArrayList<>(); // 未查询到数据的仪表
		notFoundMeterNos.addAll(meterNos);
		notFoundMeterNos.removeAll(actualFoundMeterNos);
		if (CollectionUtils.isNotEmpty(notFoundMeterNos)) {
			for (String meterNo : notFoundMeterNos) {
				MeterInfo info = meterInfoDao.queryInfo(meterNo);
				if (null != info) {
					UseType ut = new UseType(meterNo, null, null, 
							BizUtils.doubleToString(rateMap.get(meterNo) / 100),
							lineNameMap.get(meterNo), multipleMap.get(meterNo), 
							"0.00", "0.00");
					useTypes.add(renterService.buildUserType(ut, info.getSubTermCode()));
				}
			}
		}
		
		// 计费项目数据 
		if (isDividTimeUser) {
			double priceJIAN = renterConfig.getApexPrice();
			double priceFENG = renterConfig.getPeakPrice();
			double pricePING = renterConfig.getFlatPrice();
			double priceGU = renterConfig.getValleyPrice();
			CountItem item1 = new CountItem(ChargeType.JIAN.getValue(), ChargeType.JIAN.getDesc(), 
				    BizUtils.doubleToString(quantityJIAN), BizUtils.doubleToString(priceJIAN), 
					BizUtils.doubleToString(quantityJIAN * priceJIAN));
			CountItem item2 = new CountItem(ChargeType.FENG.getValue(), ChargeType.FENG.getDesc(), 
					BizUtils.doubleToString(quantityFENG), BizUtils.doubleToString(priceFENG), 
					BizUtils.doubleToString(quantityFENG * priceFENG));
			CountItem item3 = new CountItem(ChargeType.PING.getValue(), ChargeType.PING.getDesc(), 
					BizUtils.doubleToString(quantityPING), BizUtils.doubleToString(pricePING), 
					BizUtils.doubleToString(quantityPING * pricePING));
			CountItem item4 = new CountItem(ChargeType.GU.getValue(), ChargeType.GU.getDesc(), 
					BizUtils.doubleToString(quantityGU), BizUtils.doubleToString(priceGU), 
					BizUtils.doubleToString(quantityGU * priceGU));
			List<CountItem> countItems = new ArrayList<>(); 
			countItems.add(item1);  
			countItems.add(item2);
			countItems.add(item3);  
			countItems.add(item4);
			result.setCountItems(countItems);
		}
		
		result.setUseTypes(useTypes);
		return result;
	}

	@Override
	public PowerPriceConfig getPowerPriceConfig(Long companyId) {
		PowerPriceConfig powerPriceConfig = powerPriceConfigDao.queryObject(companyId);
		return powerPriceConfig;
	}

	@Override
	@Transactional
	public void save(RenterChargeOrder renterChargeOrder,RenterConfig renterConfig) {
		renterChargeOrderDao.save(renterChargeOrder);
		renterConfigDao.updateBillTaskInfo(renterConfig);
	}

	@Override
	@Transactional
	public void update(RenterChargeOrder renterChargeOrder,RenterConfig renterConfig) {
		renterChargeOrderDao.update(renterChargeOrder);
		renterConfigDao.updateBillTaskInfo(renterConfig);
	}

	@Override
	public RenterChargeOrder get(Long renterId,
			String fromDate, String toDate) {
		
		return renterChargeOrderDao.getByRenterIdAndFromDateAndToDate(renterId, fromDate, toDate);
	}

	@Override
	public boolean isExist(Long renterId, String fromDate,
			String toDate) {
		int count=renterChargeOrderDao.getCountByRenterIdAndFromDateAndToDate(renterId, fromDate, toDate);
		return count>0?true:false;
	}

	@Override
	public RenterChargeOrder getByOrderId(String orderId) {
	
		return renterChargeOrderDao.getByOrderId(orderId);
	}

	@Override
	public List<String> getPrePaymentUnsettledOrders(int limit) {
		
		return renterChargeOrderDao.getPrePaymentUnsettledOrders(limit);
	}
	

}
