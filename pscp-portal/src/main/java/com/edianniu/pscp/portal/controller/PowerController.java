package com.edianniu.pscp.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cs.bean.power.AllWarningsReqData;
import com.edianniu.pscp.cs.bean.power.ChargeBillReqData;
import com.edianniu.pscp.cs.bean.power.ChargeBillResult;
import com.edianniu.pscp.cs.bean.power.ChargeDetailReqData;
import com.edianniu.pscp.cs.bean.power.ChargeDetailResult;
import com.edianniu.pscp.cs.bean.power.CollectorPointsReqData;
import com.edianniu.pscp.cs.bean.power.CollectorPointsResult;
import com.edianniu.pscp.cs.bean.power.CompanyLinesReqData;
import com.edianniu.pscp.cs.bean.power.CompanyLinesResult;
import com.edianniu.pscp.cs.bean.power.ConsumeRankReqData;
import com.edianniu.pscp.cs.bean.power.ConsumeRankResult;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceReqData;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceResult;
import com.edianniu.pscp.cs.bean.power.EquipmentType;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeReqData;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeResult;
import com.edianniu.pscp.cs.bean.power.MonitorListReqData;
import com.edianniu.pscp.cs.bean.power.MonitorListResult;
import com.edianniu.pscp.cs.bean.power.PowerDistributeReqData;
import com.edianniu.pscp.cs.bean.power.PowerDistributeResult;
import com.edianniu.pscp.cs.bean.power.PowerFactorReqData;
import com.edianniu.pscp.cs.bean.power.PowerFactorResult;
import com.edianniu.pscp.cs.bean.power.PowerLoadReqData;
import com.edianniu.pscp.cs.bean.power.PowerLoadResult;
import com.edianniu.pscp.cs.bean.power.PowerQuantityReqData;
import com.edianniu.pscp.cs.bean.power.PowerQuantityResult;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadReqData;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadResult;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageReqData;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageResult;
import com.edianniu.pscp.cs.bean.power.UseFengGUReqData;
import com.edianniu.pscp.cs.bean.power.UseFengGUResult;
import com.edianniu.pscp.cs.bean.power.WarningListReqData;
import com.edianniu.pscp.cs.bean.power.WarningListResult;
import com.edianniu.pscp.cs.bean.power.vo.MonitorVO;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.DateUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 智能监控
 * @author zhoujianjian
 * @date 2017年12月12日 上午11:55:29
 */
@Controller
@RequestMapping("/power")
public class PowerController {
	
	@Autowired
	@Qualifier("powerInfoService")
	private PowerInfoService powerInfoService;
	
	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;
	
	/**
     * 附件前缀
     */
	@Value(value = "${file.download.url}")
    private String prefix;


    /**
     * 为附件fid加"http://192.168.1.251:8091/"前缀
     */
    private String addPrefixForFids(String txImg) {
    	if (StringUtils.isBlank(txImg)) {
			return null;
		}
        return prefix + txImg;
    }
	
	/**
	 * 智能监控>监控列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/monitorList")
	@RequiresPermissions("power:monitorList")
	public R monitorList(@RequestParam(required = false, defaultValue = "1") Integer page, 
						 @RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		
		MonitorListReqData reqData = new MonitorListReqData();
		reqData.setUid(ShiroUtils.getUserId());
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		
		MonitorListResult result = powerInfoService.getmonitorList(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		// 处理图像 添加前缀
		List<MonitorVO> monitorList = result.getMonitorList();
		if (CollectionUtils.isNotEmpty(monitorList)) {
			for (MonitorVO monitorVO : monitorList) {
				monitorVO.setTxImg(addPrefixForFids(monitorVO.getTxImg()));
			}
		}
		PageUtils pageUtils = new PageUtils(result.getMonitorList(), result.getTotalCount(), limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 智能监控>监控列表>总览>总览-当前负荷
	 * 获取总览下的当前负荷
	 * @param companyId 客户公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/loadOfNow/{companyId}")
	@RequiresPermissions("power:general:loadOfNow")
	public R loadOfNow(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		String companyName = entity.getName();
		Long uid = entity.getMemberId();
		PowerLoadReqData reqData = new PowerLoadReqData();
		reqData.setUid(uid);
		PowerLoadResult result = powerInfoService.getPowerLoad(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		if (StringUtils.isNotBlank(companyName)) {
			map.put("companyName", companyName);
		}
		if (null != result.getLoad()) {
			map.put("load", result.getLoad());
		}
		if (null != result.getPowerFactor()) {
			map.put("powerFactor", result.getPowerFactor());
		}
		if (null != result.getQuantityOfThisMonth()) {
			map.put("quantityOfThisMonth", result.getQuantityOfThisMonth());
		}
		if (null != result.getChargeOfThisMonth()) {
			map.put("chargeOfThisMonth", result.getChargeOfThisMonth());
		}
		
		return R.ok().put("loadOfNow", map);
	}
	
	/**
	 * 智能监控>监控列表>总览>总览-实时负荷
	 * 获取总览下的实时负荷
	 * @param companyId 客户公司ID
	 * @param date 查询时间，精确到日yyyy-MM-dd，可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/loadOfRealTime/{companyId}")
	@RequiresPermissions("power:general:loadOfRealTime")
	public R loadOfRealTime(@PathVariable("companyId") Long companyId,
						    @RequestBody(required = false) JSONObject jsonObject){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		// 获取查询时间
		String date = null;
		if (null != jsonObject) {
			date = jsonObject.getString("date");
			if (StringUtils.isNotBlank(date)) {
				if (null == DateUtils.parse(date)) {
					return R.error("日期格式不合法！");
				}
			}
		}
		
		RealTimeLoadReqData reqData = new RealTimeLoadReqData();
		reqData.setUid(uid);
		reqData.setDate(date);
		RealTimeLoadResult result = powerInfoService.getRealTimeLoad(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		if (null != result.getLoadsOfToday()) {
			map.put("loadsOfToday", result.getLoadsOfToday());
		}
		if (null != result.getMaxLoadOfToday()) {
			map.put("maxLoadOfToday", result.getMaxLoadOfToday());
		}
		if (null != result.getLoadsOfLastDay()) {
			map.put("loadsOfLastDay", result.getLoadsOfLastDay());
		}
		if (null != result.getMaxLoadOfLastDay()) {
			map.put("maxLoadOfLastDay", result.getMaxLoadOfLastDay());
		}
		return R.ok().put("loadOfRealTime", map);
	}
	
	/**
	 * 智能监控>监控列表>总览>总览-用电量
	 * 获取总览下的用电量
	 * @param companyId 客户公司ID
	 * @param date 查询时间，精确到月yyyy-MM，可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/quantity/{companyId}")
	@RequiresPermissions("power:general:quantity")
	public R quantity(@PathVariable("companyId") Long companyId, 
			@RequestBody(required = false) JSONObject jsonObject){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		// 获取查询时间
		String date = null;
		if (null != jsonObject) {
			date = jsonObject.getString("date");
			if (StringUtils.isNotBlank(date)) {
				if (!BizUtils.checkYearAndMonth(date)) {
					return R.error("日期格式不合法！");
				}
			}
		}
		PowerQuantityReqData reqData = new PowerQuantityReqData();
		reqData.setUid(uid);
		reqData.setDate(date);
		PowerQuantityResult result = powerInfoService.getPowerQuantity(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		if (null != result.getQuantitiesOfThisMonths()) {
			map.put("quantitiesOfThisMonths", result.getQuantitiesOfThisMonths());
		}
		if (null != result.getMaxQuantityOfThisMonth()) {
			map.put("maxQuantityOfThisMonth", result.getMaxQuantityOfThisMonth());
		}
		if (null != result.getQuantitiesOfLastMonths()) {
			map.put("quantitiesOfLastMonths", result.getQuantitiesOfLastMonths());
		}
		if (null != result.getMaxQuantityOfLastMonth()) {
			map.put("maxQuantityOfLastMonth", result.getMaxQuantityOfLastMonth());
		}
		
		return R.ok().put("quantity", map);
	}
	
	/**
	 * 智能监控>监控列表>总览>总览-电量分布
	 * 获取总览下的电量分布
	 * @param companyId 客户公司ID
	 * @param date 查询时间，精确到月yyyy-MM，可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/powerDistribute/{companyId}")
	@RequiresPermissions("power:general:powerDistribute")
	public R powerDistribute(@PathVariable("companyId") Long companyId, 
							@RequestBody(required = false) JSONObject jsonObject){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String date = null;
		if (null != jsonObject) {
			date = jsonObject.getString("data");
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					return R.error("日期格式不合法！");
				}
			}
		}
		PowerDistributeReqData reqData = new PowerDistributeReqData();
		reqData.setUid(uid);
		reqData.setDate(date);
		PowerDistributeResult result = powerInfoService.getPowerDistribute(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		if (null != result.getChargeMode()) { // 电度电费计费方式(0普通       1分时)
			map.put("chargeMode", result.getChargeMode());
		}
		if (null != result.getQuantities()) {
			map.put("quantities", result.getQuantities());
		}
		if (null != result.getCharges()) {
			map.put("charges", result.getCharges());
		}
		
		return R.ok().put("powerDistribute", map);
	}
	
	/**
	 * 智能监控>监控列表>总览>分项（综合、动力、照明、空调、特殊）
	 * 获取总览下的各类设备的最高负荷和用电量
	 * @param companyId   客户公司ID
	 * @param type        查询类别：  0-综合  1-动力  2-照明  3-空调  4-特殊  
	 * @param date        查询日期，精确到日期yyyy-MM-dd，可为空(默认当前日期)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/generalByType/{companyId}/{type}")
	@RequiresPermissions("power:general:generalByType")
	public R getGeneralByType(@PathVariable("companyId") Long companyId, 
							  @PathVariable("type") Integer type, 
							  @RequestParam(required = false) String date, 
							  @RequestParam(required = false, defaultValue = "1") Integer page, 
							  @RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		String companyName = entity.getName();
		Long uid = entity.getMemberId();
		if (!EquipmentType.isExist(type)) {
			return R.error("type不合法");
		}
		if (StringUtils.isNotBlank(date)) {
			if (null == DateUtils.parse(date)) {
				return R.error("日期格式不合法！");
			}
		}
		
		GeneralByTypeReqData reqData = new GeneralByTypeReqData();
		reqData.setUid(uid);
		reqData.setType(type);
		reqData.setDate(date);
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		GeneralByTypeResult result = powerInfoService.getGeneralByType(reqData);
		
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		if (StringUtils.isNotBlank(companyName)) {
			map.put("companyName", companyName);
		}
		PageUtils pageUtils = new PageUtils(result.getCollectorPoints(), result.getTotalCount(), limit, page);
		map.put("pageUtils", pageUtils);
		
		return R.ok().put("page", map);
	}
	
	/**
	 * 智能监控>监控列表>总览>告警
	 * 获取总览下的告警列表
	 * @param companyId  客户公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/general/GeneralWarnings/{companyId}")
	@RequiresPermissions("power:general:GeneralWarnings")
	public R getGeneralWarnings(@PathVariable("companyId") Long companyId, 
								@RequestParam(required = false, defaultValue = "1") Integer page, 
								@RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		String companyName = entity.getName();
		Long uid = entity.getMemberId();
		
		WarningListReqData reqData = new WarningListReqData();
		reqData.setUid(uid);
		reqData.setPageSize(limit);
		reqData.setOffset(offset);
		WarningListResult result = powerInfoService.getWarningList(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		if (StringUtils.isNotBlank(companyName)) {
			map.put("companyName", companyName);
		}
		PageUtils pageUtils = new PageUtils(result.getWarnings(), result.getTotalCount(), limit, page);
		map.put("pageUtils", pageUtils);
		
		return R.ok().put("page", map);
	}
	
	/**
	 * 智能监控>告警
	 * 获取服务商所有客户的告警列表
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/allWarnings")
	@RequiresPermissions("power:allWarnings")
	public R getAllWarnings(@RequestParam(required = false, defaultValue = "1") Integer page, 
			 		        @RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		
		AllWarningsReqData reqData = new AllWarningsReqData();
		reqData.setUid(ShiroUtils.getUserId());
		//reqData.setUid(1090L);
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		
		WarningListResult result = powerInfoService.getAllWarnings(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		PageUtils pageUtils = new PageUtils(result.getWarnings(), result.getTotalCount(), limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 智能监控>监控列表>参考电费
	 * @param companyId 客户公司ID
	 * @param date      查询时间，精确到月yyyy-MM，可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/referCharges/{companyId}")
	@RequiresPermissions("power:referCharges")
	public R getReferCharges(@PathVariable("companyId") Long companyId, 
							 @RequestBody(required = false) JSONObject jsonObject){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		
		String date = null;
		if (null != jsonObject) {
			date = jsonObject.getString("date");
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					return R.error("日期格式不合法！");
				}
			}
		}
		ChargeBillReqData chargeBillReqData = new ChargeBillReqData();
		chargeBillReqData.setUid(uid);
		chargeBillReqData.setDate(date);
		ChargeBillResult chargeBillResult = powerInfoService.getChargeBill(chargeBillReqData);
		if (! chargeBillResult.isSuccess()) {
			return R.error(chargeBillResult.getResultCode(), chargeBillResult.getResultMessage());
		}
		ChargeDetailReqData chargeDetailReqData = new ChargeDetailReqData();
		chargeDetailReqData.setUid(uid);
		chargeDetailReqData.setDate(date);
		ChargeDetailResult chargeDetailResult = powerInfoService.getChargeDetail(chargeDetailReqData);
		if (! chargeDetailResult.isSuccess()) {
			return R.error(chargeDetailResult.getResultCode(), chargeDetailResult.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		map.put("chargeBill", chargeBillResult);
		map.put("chargeDetail", chargeDetailResult);
		
		return R.ok().put("referCharges", map);
	}
	
	/**
	 * 智能监控>监控列表>用能排行
	 * @param companyId 客户公司ID
	 * @param date      查询时间，精确到月yyyy-MM，可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/consumeRank/{companyId}")
	@RequiresPermissions("power:consumeRank")
	public R getConsumeRank(@PathVariable("companyId") Long companyId, 
							@RequestParam(required = false) String date, 
							@RequestParam(required = false, defaultValue = "1") Integer page, 
							@RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		if (StringUtils.isNotBlank(date)) {
			if (! BizUtils.checkYearAndMonth(date)) {
				return R.error("日期格式不合法！");
			}
		}
		
		ConsumeRankReqData reqData = new ConsumeRankReqData();
		reqData.setUid(uid);
		reqData.setDate(date);
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		
		ConsumeRankResult result = powerInfoService.getConsumeRank(reqData );
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		PageUtils pageUtils = new PageUtils(result.getBuildings(), result.getTotalCount(), limit, page);
		map.put("pageUtils", pageUtils);
		
		return R.ok().put("page", map);
	}
	
	/**
	 * 智能监控>监控列表>电压健康
	 * @param companyId   客户公司ID
	 */
	@ResponseBody
	@RequestMapping("/safetyVoltage/{companyId}")
	@RequiresPermissions("power:safetyVoltage")
	public R getSafetyVoltage(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		
		SafetyVoltageReqData reqData = new SafetyVoltageReqData();
		reqData.setUid(uid);
		SafetyVoltageResult result = powerInfoService.getSafetyVoltage(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		if (null != result.getRateOfQualified()) {
			map.put("rateOfQualified", result.getRateOfQualified());
		}
		if (null != result.getUpLimit()) {
			map.put("upLimit", result.getUpLimit());
		}
		if (null != result.getDownLimit()) {
			map.put("downLimit", result.getDownLimit());
		}
		if (null != result.getUaOfNow()) {
			map.put("uaOfNow", result.getUaOfNow());
		}
		if (null != result.getUbOfNow()) {
			map.put("ubOfNow", result.getUbOfNow());
		}
		if (null != result.getUcOfNow()) {
			map.put("ucOfNow", result.getUcOfNow());
		}
		if (null != result.getUas()) {
			map.put("uas", result.getUas());
		}
		if (null != result.getUbs()) {
			map.put("ubs", result.getUbs());
		}
		if (null != result.getUcs()) {
			map.put("ucs", result.getUcs());
		}
		
		return R.ok().put("safetyVoltage", map);
	}
	
	/**
	 * 智能监控>监控列表>电流平衡
	 * @param companyId  客户公司ID
	 * @param lineId     线路ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/currentBalance/{companyId}")
	@RequiresPermissions("power:currentBalance")
	public R getCurrentBalance(@PathVariable("companyId") Long companyId,
							   @RequestParam(required = false) Long lineId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		
		CurrentBalanceReqData reqData = new CurrentBalanceReqData();
		reqData.setUid(uid);
		reqData.setLineId(lineId);
		CurrentBalanceResult result = powerInfoService.getCurrentBalance(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		map.put("lineId", lineId);
		if (null != result.getLineName()) {
			map.put("lineName", result.getLineName());
		}
		if (null != result.getaPhase()) {
			map.put("aPhase", result.getaPhase());
		}
		if (null != result.getbPhase()) {
			map.put("bPhase", result.getbPhase());
		}
		if (null != result.getcPhase()) {
			map.put("cPhase", result.getcPhase());
		}
		if (null != result.getUnbalanceDegreeOfNow()) {
			map.put("unbalanceDegreeOfNow", result.getUnbalanceDegreeOfNow());
		}
		if (null != result.getUnbalanceDegrees()) {
			map.put("unbalanceDegrees", result.getUnbalanceDegrees());
		}
		if (null != result.getIsBalance()) {
			map.put("isBalance", result.getIsBalance());
		}
		if (null != result.getaUnbalancePointList()) {
			map.put("aUnbalancePointList", result.getaUnbalancePointList());
		}
		if (null != result.getbUnbalancePointList()) {
			map.put("bUnbalancePointList", result.getbUnbalancePointList());
		}
		if (null != result.getcUnbalancePointList()) {
			map.put("cUnbalancePointList", result.getcUnbalancePointList());
		}
		
		//String jsonString = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		//System.out.println(jsonString);
		
		return R.ok().put("currentBalance", map);
	}
	
	/**
	 * 客户线路列表
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customerLines/{companyId}")
	public R getCustomerLines(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		
		CompanyLinesReqData reqData = new CompanyLinesReqData();
		reqData.setUid(uid);
		CompanyLinesResult result = powerInfoService.getCompanyLines(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		
		return R.ok().put("lines", result.getLines());
	}
	
	
	/**
	 * 智能监控>监控列表>功率因数
	 * @param companyId
	 * @param lineId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/powerFactor/{companyId}")
	@RequiresPermissions("power:powerFactor")
	public R getPowerFactor(@PathVariable("companyId") Long companyId,
							@RequestParam(required = false) Long lineId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		
		PowerFactorReqData reqData = new PowerFactorReqData();
		reqData.setUid(uid);
		reqData.setLineId(lineId);
		PowerFactorResult result = powerInfoService.getPowerFactor(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		map.put("awardMoney", result.getAwardMoney());
		map.put("awardRate", result.getAwardRate());
		map.put("powerFactorOfThisMonth", result.getPowerFactor());
		map.put("powerFactors", result.getPowerFactors());
		map.put("powerFactorOfNow", result.getPowerFactorOfNow());
		map.put("powerFactorLimits", result.getLimits());

		return R.ok().put("powerFactor", map);
	}
	
	/**
	 * 采集点信息列表，带分页
	 * 功率因数---获取监测点功率因数
	 */
	@ResponseBody
	@RequestMapping("/collectorPoints/powerFactor/{companyId}")
	public R getCollectorPointsForPowerFactor(@PathVariable("companyId") Long companyId,
			@RequestParam(required = false, defaultValue = "1") Integer page, 
			@RequestParam(required = false, defaultValue = "10") Integer limit){
		Integer offset = (page - 1) * limit;
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		
		CollectorPointsReqData reqData = new CollectorPointsReqData();
		reqData.setUid(uid);
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		CollectorPointsResult result = powerInfoService.getCollectorPointsForPowerFactor(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		PageUtils pageUtils = new PageUtils(result.getCollectorPoints(), result.getTotalCount(), limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 采集点信息列表，带分页
	 * 峰谷利用---获取监测点用电量
	 * @param period查询时间区间      格式：HH:mm-HH:mm
	 */
	@ResponseBody
	@RequestMapping("/collectorPoints/powerQuantity/{companyId}")
	public R getCollectorPointsForPowerQuantity(@PathVariable("companyId") Long companyId,
			@RequestParam(required = false, defaultValue = "1") Integer page, 
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false) String period){
		// 如果时间为空，则什么也不返回
		if (StringUtils.isBlank(period)) {
			PageUtils pageUtils = new PageUtils(new ArrayList<>(), 0, limit, page);
			return R.ok().put("page", pageUtils);
		}
		// 时间格式验证
		String[] split = period.split("-");
		if (split.length != 2) {
			return R.error("时间格式不正确");
		}
		String startTime = split[0];
		String endTime = split[1];
		boolean isTrue1 = BizUtils.cheackTimePattern(startTime);
		boolean isTrue2 = BizUtils.cheackTimePattern(endTime);
		if (! (isTrue1 && isTrue2)) {
			return R.error("时间格式不正确");
		}
		Integer offset = (page - 1) * limit;
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		
		CollectorPointsReqData reqData = new CollectorPointsReqData();
		reqData.setUid(entity.getMemberId());
		reqData.setLimit(limit);
		reqData.setOffset(offset);
		reqData.setStartTime(startTime);
		reqData.setEndTime(endTime);
		CollectorPointsResult result = powerInfoService.getCollectorPointsForPowerQuantity(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		PageUtils pageUtils = new PageUtils(result.getCollectorPoints(), result.getTotalCount(), limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 智能监控>监控列表>峰谷利用
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/useFengGU/{companyId}")
	@RequiresPermissions("power:useFengGU")
	public R useFengGU(@PathVariable("companyId") Long companyId,
					   @RequestParam(required = false) Long lineId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		Long uid = entity.getMemberId();
		String companyName = entity.getName();
		
		UseFengGUReqData reqData = new UseFengGUReqData();
		reqData.setUid(uid);
		reqData.setLineId(lineId);
		UseFengGUResult result = powerInfoService.useFengGU(reqData);
		if (! result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("", "");
		map.put("lineId", lineId);
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		map.put("quantityOfToday", result.getQuantityOfToday());
		map.put("quantityOfGu", result.getQuantityOfGu());
		map.put("chargeOfToday", result.getChargeOfToday());
		map.put("chargeOfGu", result.getChargeOfGu());
		map.put("distributeOfTimes", result.getDistributeOfTimes());
		map.put("quantities", result.getQuantities());
		map.put("period", result.getPeriod());
		
		return R.ok().put("useFengGU", map);
	}
	
	
	
}
