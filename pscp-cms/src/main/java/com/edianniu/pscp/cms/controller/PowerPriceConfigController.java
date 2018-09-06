package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.*;
import com.edianniu.pscp.cms.bean.req.PowerPriceConfigReqData;
import com.edianniu.pscp.cms.commons.CacheKey;
import com.edianniu.pscp.cms.commons.MemberType;
import com.edianniu.pscp.cms.entity.CompanyEntity;
import com.edianniu.pscp.cms.entity.PowerOtherConfigEntity;
import com.edianniu.pscp.cms.entity.PowerPriceConfigEntity;
import com.edianniu.pscp.cms.service.CompanyService;
import com.edianniu.pscp.cms.service.PowerOtherConfigService;
import com.edianniu.pscp.cms.service.PowerPriceConfigService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.MoneyUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cs.bean.power.BaseChargeModeType;
import com.edianniu.pscp.cs.bean.power.ChargeModeType;
import com.edianniu.pscp.cs.bean.power.PowerConfigType;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 企业电价及电压、功率因数、用电负荷相关参数配置管理
 * @author zhoujianjian
 * @date 2018年1月3日 下午8:19:13
 */
@Controller
@RequestMapping("/powerPriceConfig")
public class PowerPriceConfigController extends AbstractController{
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PowerPriceConfigService powerPriceConfigService;
	
	@Autowired
	private PowerOtherConfigService powerOtherConfigService;
	
	@Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String index(){
		return "company/smart-config.html";
	}
	
	/**
	 * 电压参数配置新增、编辑
	 * @param companyId 客户公司ID
	 * @param up   上限         1
	 * @param down 下限         2
	 */
	@ResponseBody
	@RequestMapping("/voltageConfig/save/{companyId}")
	@RequiresPermissions("voltageConfig:save")
	public R saveVoltageConfig(@PathVariable("companyId") Long companyId, @RequestBody JSONObject jsonObject){
		CompanyEntity companyEntity = companyService.getCompanyByCompanyId(companyId);
		if (null == companyEntity) {
			return R.error("companyId不合法");
		}
		Integer memberType = companyEntity.getMemberType();
		Integer status = companyEntity.getStatus();
		if (MemberType.CUSTOMER.getValue() != memberType || 2 != status) {
			return R.error("要配置的用户尚未认证为客户");
		}
		String up = jsonObject.getString("up");
		String down = jsonObject.getString("down");
		boolean checkUp = BizUtils.isPositiveNumber(up);
		boolean checkDown = BizUtils.isPositiveNumber(down);
		if (!(checkUp && checkDown) || Double.valueOf(down).compareTo(Double.valueOf(up)) >= 0) {
			return R.error("参数配置不合法");
		}
		// 上限
		PowerOtherConfigEntity entity1 = new PowerOtherConfigEntity();
		entity1.setCompanyId(companyId);
		entity1.setType(PowerConfigType.VOLTAGE.getValue());
		entity1.setKey(1);
		entity1.setKeyName("上限");
		entity1.setValue(up);
		// 下限
		PowerOtherConfigEntity entity2 = new PowerOtherConfigEntity();
		entity2.setCompanyId(companyId);
		entity2.setType(PowerConfigType.VOLTAGE.getValue());
		entity2.setKey(2);
		entity2.setKeyName("下限");
		entity2.setValue(down);
		PowerOtherConfigEntity[] entities = {entity1, entity2};
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_VOLTAGG_CONFIG_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		
		powerOtherConfigService.save(entities);
		return R.ok().put("msg", "成功");
	}
	
	/**
	 * 功率因数参数配置新增、编辑
	 * @param companyId 客户公司ID
	 * @param serious   严重         1    格式：xxx-xxx
	 * @param low       低             2    格式：xxx-xxx
	 * @param normal    正常         3    格式：xxx-xxx
	 * @param well      好             4    格式：xxx-xxx
	 */
	@ResponseBody
	@RequestMapping("/powerFactor/save/{companyId}")
	@RequiresPermissions("powerFactor:save")
	public R savePowerFactorConfig(@PathVariable("companyId") Long companyId, @RequestBody JSONObject jsonObject){
		CompanyEntity companyEntity = companyService.getCompanyByCompanyId(companyId);
		if (null == companyEntity) {
			return R.error("companyId不合法");
		}
		Integer memberType = companyEntity.getMemberType();
		Integer status = companyEntity.getStatus();
		if (MemberType.CUSTOMER.getValue() != memberType || 2 != status) {
			return R.error("要配置的用户尚未认证为客户");
		}
		String serious = jsonObject.getString("serious");
		String low = jsonObject.getString("low");
		String normal = jsonObject.getString("normal");
		String well = jsonObject.getString("well");
		String[] strings = {serious, low, normal, well};
		// 判断字符串是否合法
		boolean isLegal = isLegalNumStrings(strings);
		if (!isLegal) {
			return R.error("参数不合法");
		}
		// 判断是否从0开始，到1结束
		String start = serious.split("-")[0];
		String end = well.split("-")[1];
		int isZero = Double.valueOf(start).compareTo(0.0);
		int isOne = Double.valueOf(end).compareTo(1.0);
		if (0 != isZero || 0 != isOne) {
			return R.error("参数不合法");
		}
		// 严重
		PowerOtherConfigEntity entity1 = new PowerOtherConfigEntity();
		entity1.setCompanyId(companyId);
		entity1.setType(PowerConfigType.POWER_FACTOR.getValue());
		entity1.setKey(1);
		entity1.setKeyName("严重");
		entity1.setValue(serious);
		// 低
		PowerOtherConfigEntity entity2 = new PowerOtherConfigEntity();
		entity2.setCompanyId(companyId);
		entity2.setType(PowerConfigType.POWER_FACTOR.getValue());
		entity2.setKey(2);
		entity2.setKeyName("低");
		entity2.setValue(low);
		// 正常
		PowerOtherConfigEntity entity3 = new PowerOtherConfigEntity();
		entity3.setCompanyId(companyId);
		entity3.setType(PowerConfigType.POWER_FACTOR.getValue());
		entity3.setKey(3);
		entity3.setKeyName("正常");
		entity3.setValue(normal);
		// 好
		PowerOtherConfigEntity entity4 = new PowerOtherConfigEntity();
		entity4.setCompanyId(companyId);
		entity4.setType(PowerConfigType.POWER_FACTOR.getValue());
		entity4.setKey(4);
		entity4.setKeyName("好");
		entity4.setValue(well);
		PowerOtherConfigEntity[] entities = {entity1, entity2, entity3, entity4};
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_POWER_FACTOR_CONFIG_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		
		powerOtherConfigService.save(entities);
		return R.ok().put("msg", "成功");
	}
	
	/**
	 * 用电负荷参数配置新增、编辑
	 * @param companyId 客户公司ID
	 * @param low         低               1   格式：xxx-xxx
	 * @param economic    经济           2   格式：xxx-xxx
	 * @param warn        警戒           3   格式：xxx-xxx
	 * @param over        越界           4   格式：xxx-xxx
	 */
	@ResponseBody
	@RequestMapping("/load/save/{companyId}")
	@RequiresPermissions("load:save")
	public R saveLoadConfig(@PathVariable("companyId") Long companyId, @RequestBody JSONObject jsonObject){
		CompanyEntity companyEntity = companyService.getCompanyByCompanyId(companyId);
		if (null == companyEntity) {
			return R.error("companyId不合法");
		}
		Integer memberType = companyEntity.getMemberType();
		Integer status = companyEntity.getStatus();
		if (MemberType.CUSTOMER.getValue() != memberType || 2 != status) {
			return R.error("要配置的用户尚未认证为客户");
		}
		String low = jsonObject.getString("low");
		String economic = jsonObject.getString("economic");
		String warn = jsonObject.getString("warn");
		String over = jsonObject.getString("over");
		String[] strings = {low, economic, warn, over};
		// 判断字符串是否合法
		boolean isLegal = isLegalNumStrings(strings);
		if (!isLegal) {
			return R.error("参数不合法");
		}
		// 低 
		PowerOtherConfigEntity entity1 = new PowerOtherConfigEntity();
		entity1.setCompanyId(companyId);
		entity1.setType(PowerConfigType.LOAD.getValue());
		entity1.setKey(1);
		entity1.setKeyName("低");
		entity1.setValue(low);
		// 经济
		PowerOtherConfigEntity entity2 = new PowerOtherConfigEntity();
		entity2.setCompanyId(companyId);
		entity2.setType(PowerConfigType.LOAD.getValue());
		entity2.setKey(2);
		entity2.setKeyName("经济");
		entity2.setValue(economic);
		// 警戒
		PowerOtherConfigEntity entity3 = new PowerOtherConfigEntity();
		entity3.setCompanyId(companyId);
		entity3.setType(PowerConfigType.LOAD.getValue());
		entity3.setKey(3);
		entity3.setKeyName("警戒");
		entity3.setValue(warn);
		// 越界
		PowerOtherConfigEntity entity4 = new PowerOtherConfigEntity();
		entity4.setCompanyId(companyId);
		entity4.setType(PowerConfigType.LOAD.getValue());
		entity4.setKey(4);
		entity4.setKeyName("越界");
		entity4.setValue(over);
		PowerOtherConfigEntity[] entities = {entity1, entity2, entity3, entity4};
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_LOAD_CONFIG_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		
		powerOtherConfigService.save(entities);
		return R.ok().put("msg", "成功");
	}
	
	/**
	 * 1.判断字符串结构是否为“数字1-数字2”结构
	 * 2.判断数字是否升序排列
	 * 3.判断边界数字能否重合
	 */
	public boolean isLegalNumStrings(String[] strings){
		List<String> list = new ArrayList<>();
		// 判断是否是“数字1-数字2”结构，并按先后顺序构建所有数字集合
		for (String string : strings) {
			if (!BizUtils.checkNum2Num(string)) {
				return false;
			}
			list.addAll(Arrays.asList(string.split("-")));
		}
		// 判断数字集合是否升序
		for (int i = 0; i < list.size() - 1; i++) {
			int compareTo = Double.valueOf(list.get(i)).compareTo(Double.valueOf(list.get(i + 1)));
			if (compareTo > 0) {
				return false;
			}
		}
		// 判断前后两个字符串能否连接起来，如[0-0.2, 0.2-0.4, 0.4-0.8, 0.8-1]
		// 构建成集合后为 0, 0.2, 0.2, 0.4, 0.4, 0.8, 0.8, 1
		for (int j = 1; j < list.size() - 2; j = j + 2) {
			int compareTo = Double.valueOf(list.get(j)).compareTo(Double.valueOf(list.get(j + 1)));
			if (compareTo != 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 电压、功率因数、用电负荷配置详情
	 * @param companyId 客户公司ID
	 * @param type      配置类型   1:负荷   2:功率因数  3:电压
	 */
	@ResponseBody
	@RequestMapping("/otherConfig/info/{companyId}/{type}")
	@RequiresPermissions("otherConfig:info")
	public R otherConfigInfo(@PathVariable("companyId") Long companyId, @PathVariable("type") Integer type){
		Boolean exist = PowerConfigType.isExist(type);
		if (! exist) {
			return R.error("type不合法");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("type", type);
		List<PowerOtherConfigEntity> list = powerOtherConfigService.queryConfigs(map);
		return R.ok().put("list", list);
	}
	
	/**
	 * 电压、功率因数、用电负荷配置删除
	 * @param companyId 客户公司ID
	 * @param type      配置类型   1:负荷   2:功率因数  3:电压
	 */
	@ResponseBody
	@RequestMapping("/otherConfig/delete/{companyId}/{type}")
	@RequiresPermissions("otherConfig:delete")
	public R otherConfigDelete(@PathVariable("companyId") Long companyId, @PathVariable("type") Integer type){
		Boolean exist = PowerConfigType.isExist(type);
		if (! exist) {
			return R.error("type不合法");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("type", type);
		List<PowerOtherConfigEntity> list = powerOtherConfigService.queryConfigs(map);
		if (CollectionUtils.isEmpty(list)) {
			return R.error("要删除的配置不存在");
		}
		powerOtherConfigService.deleteConfigs(companyId, type);
		
		return R.ok().put("msg", "成功");
	}
	
	/**
	 * 电费相关参数配置新增、编辑
	 * @param companyId
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/priceConfig/save")
	@RequiresPermissions("priceConfig:save")
	public R savePriceConfig(@RequestBody PowerPriceConfigReqData req){
		// 客户公司信息验证
		Long companyId = req.getCompanyId();
		if (null == companyId) {
			return R.error("companyId不能为空");
		}
		CompanyEntity companyEntity = companyService.getCompanyByCompanyId(companyId);
		if (null == companyEntity) {
			return R.error("companyId不合法");
		}
		Integer memberType = companyEntity.getMemberType();
		Integer status = companyEntity.getStatus();
		if (MemberType.CUSTOMER.getValue() != memberType || 2 != status) {
			return R.error("要配置的用户尚未认证为客户");
		}
		// 计费周期
		String chargeTimeInterval = req.getChargeTimeInterval();
		if (!BizUtils.isPositiveInt(chargeTimeInterval)) {
			return R.error("计费周期必须为正整数");
		}
		Integer value = Integer.valueOf(chargeTimeInterval);
		if (value > 31 || value < 1) {
			return R.error("计费周期必须1-31之间的正整数");
		}
		// 电度电费计费方式   1分时       0普通
		Integer chargeMode = req.getChargeMode();
		if (null == chargeMode) {
			return R.error("电度电费计费方式不能为空");
		}
		boolean exist1 = ChargeModeType.isExist(chargeMode);
		if (! exist1) {
			return R.error("电度电费计费方式不合法");
		}
		if (chargeMode.equals(ChargeModeType.NORMAL.getValue())) {
			/* 普通（统一单价） */
			if (!MoneyUtils.checkMoney(req.getBasePrice())) {
				return R.error("统一单价不合法");
			}
		} else {
			/* 分时单价  */   // 尖
			JSONArray apexTimeInterval = req.getApexTimeInterval();
			if (CollectionUtils.isNotEmpty(apexTimeInterval)) {
				if (!MoneyUtils.checkMoney(req.getApexPrice())) {
					return R.error("尖单价设置不合法");
				}
			}
			// 峰
			JSONArray peakTimeInterval = req.getPeakTimeInterval();
			if (CollectionUtils.isNotEmpty(peakTimeInterval)) {
				if (!MoneyUtils.checkMoney(req.getPeakPrice())) {
					return R.error("峰单价设置不合法");
				}
			}
			// 平
			JSONArray flatTimeInterval = req.getFlatTimeInterval();
			if (CollectionUtils.isNotEmpty(flatTimeInterval)) {
				if (!MoneyUtils.checkMoney(req.getFlatPrice())) {
					return R.error("平单价设置不合法");
				}
			}
			// 谷
			JSONArray valleyTimeInterval = req.getValleyTimeInterval();
			if (CollectionUtils.isNotEmpty(valleyTimeInterval)) {
				if (!MoneyUtils.checkMoney(req.getValleyPrice())) {
					return R.error("谷单价设置不合法");
				}
			}
			// 验证尖峰平谷时间段是否合法
			boolean check = checkJFPG(apexTimeInterval, peakTimeInterval, flatTimeInterval, valleyTimeInterval);
			if (!check) {
				return R.error("尖峰平谷时段设置不合法");
			}
		}
		// 变压器容量
		Double transformerCapacity = req.getTransformerCapacity();
		if (null == transformerCapacity) {
			return R.error("变压器容量不能为空");
		}
		if (transformerCapacity < 0) {
			return R.error("变压器容量不合法");
		}
		// 基本电费计费方式:    1变压器容量        2最大需量
		Integer baseChargeMode = req.getBaseChargeMode();
		boolean exist2 = BaseChargeModeType.isExist(baseChargeMode);
		if (!exist2) {
			return R.error("基本电费计费方式不能为空");
		}
		if (baseChargeMode.equals(BaseChargeModeType.MAX_CAPACITY.getValue())) {
			/* 按变压器容量 */
			Double transformerCapacityPrice = req.getTransformerCapacityPrice();
			if (!MoneyUtils.checkMoney(transformerCapacityPrice)) {
				return R.error("容量单价设置不合法");
			}
		} else {
			/* 按最大需量 */
			Double maxCapacity = req.getMaxCapacity();
			Double maxCapacityPrice = req.getMaxCapacityPrice();
			if (null == maxCapacity || null == maxCapacityPrice) {
				return R.error("最大需量和需量单价不能为空");
			}
			if (maxCapacity < 0 || maxCapacityPrice < 0) {
				return R.error("最大需量或需量单价不合法");
			}
		}
		// 力调标准
		Double standardAdjustRate = req.getStandardAdjustRate();
		if (null == standardAdjustRate) {
			return R.error("力调标准不能为空");
		}
		// 功率因数国家标准值
		List<Double> standerds = new ArrayList<>();
		standerds.add(0.80);
		standerds.add(0.85);
		standerds.add(0.90);
		if (! standerds.contains(standardAdjustRate)) {
			return R.error("力调标准不合法");
		}
		
		PowerPriceConfigEntity entity = new PowerPriceConfigEntity();
		BeanUtils.copyProperties(req, entity);
		entity.setBasePrice(req.getBasePrice() == null ? 0.00 : req.getBasePrice());
		entity.setApexPrice(req.getApexPrice() == null ? 0.00 : req.getApexPrice());
		entity.setPeakPrice(req.getPeakPrice() == null ? 0.00 : req.getPeakPrice());
		entity.setFlatPrice(req.getFlatPrice() == null ? 0.00 : req.getFlatPrice());
		entity.setValleyPrice(req.getValleyPrice() == null ? 0.00 : req.getValleyPrice());
		entity.setMaxCapacity(req.getMaxCapacity() == null ? 0.00 : req.getMaxCapacity());
		entity.setMaxCapacityPrice(req.getMaxCapacityPrice() == null ? 0.00 : req.getMaxCapacityPrice());
		entity.setTransformerCapacityPrice(req.getTransformerCapacityPrice() == null ? 0.00 : req.getTransformerCapacityPrice());
		
		if (chargeMode.equals(ChargeModeType.NORMAL.getValue())) {
			List<Object> arrayList = new ArrayList<>();
			entity.setApexTimeInterval(JSON.toJSONString(arrayList));
			entity.setPeakTimeInterval(JSON.toJSONString(arrayList));
			entity.setFlatTimeInterval(JSON.toJSONString(arrayList));
			entity.setValleyTimeInterval(JSON.toJSONString(arrayList));
		} else {
			entity.setApexTimeInterval(JSON.toJSONString(req.getApexTimeInterval()));
			entity.setPeakTimeInterval(JSON.toJSONString(req.getPeakTimeInterval()));
			entity.setFlatTimeInterval(JSON.toJSONString(req.getFlatTimeInterval()));
			entity.setValleyTimeInterval(JSON.toJSONString(req.getValleyTimeInterval()));
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_PRICE_CONFIG_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		
		powerPriceConfigService.save(entity);
		return R.ok().put("msg", "成功");
	}
	
	/**
	 *  验证尖峰平谷时间段是否合法
	 */
	@SafeVarargs
	public static boolean checkJFPG(List<Object> ... lists){
		List<String> initList = new ArrayList<>();
    	for (List<Object> list : lists) {
			for (Object object : list) {
				String timeSpace = (String) object;
				// 检查“时:分-时:分”结构
				boolean checkTime2Time = BizUtils.checkTime2Time(timeSpace);
				if (!checkTime2Time) {
					return false;
				}
				// 检查“-”号前后时间是否相同，如果相同则无效
				String[] split = timeSpace.split("-");
				if (split[0].equals(split[1])) {
					return false;
				}
				initList.add(timeSpace);
			}
		}
    	Collections.sort(initList);
    	// 判断是否以00:00开始，是否以24:00开始
    	boolean start = initList.get(0).split("-")[0].equals("00:00");
    	boolean end = initList.get(initList.size() - 1).split("-")[1].equals("24:00");
    	if (!(start && end)) {
			return false;
		}
    	// 判断前后两个字符串能否连接起来，如[00:00-02:00, 02:00-08:00, 08:00-18:00, 18:00-19:00]
		for (int j = 0; j < initList.size() - 1; j++) {
			boolean equals = initList.get(j).split("-")[1].equals(initList.get(j + 1).split("-")[0]);
			if (!equals) {
				return false;
			}
		}
    	return true;
    }
	
	/**
	 * 电费配置详情
	 */
	@ResponseBody
	@RequestMapping("/priceConfig/info/{companyId}")
	@RequiresPermissions("priceConfig:info")
	public R priceConfigInfo(@PathVariable("companyId") Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		PowerPriceConfigEntity entity = powerPriceConfigService.queryObject(map);
		
		return R.ok().put("entity", entity);
	}
	
	/**
	 * 电费配置删除
	 */
	@ResponseBody
	@RequestMapping("/priceConfig/delete/{companyId}")
	@RequiresPermissions("priceConfig:delete")
	public R priceConfigDelete(@PathVariable("companyId") Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		PowerPriceConfigEntity entity = powerPriceConfigService.queryObject(map);
		if (null == entity) {
			return R.error("要删除的配置不存在");
		}
		powerPriceConfigService.delete(entity.getId());
		
		return R.ok().put("msg", "成功");
	}
	
}
