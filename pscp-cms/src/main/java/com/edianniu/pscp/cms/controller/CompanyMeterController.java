package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.cms.bean.MeterStatus;
import com.edianniu.pscp.cms.commons.CacheKey;
import com.edianniu.pscp.cms.commons.Constants;
import com.edianniu.pscp.cms.entity.CompanyMeterEntity;
import com.edianniu.pscp.cms.service.CompanyMeterService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cs.bean.power.HasDataResult;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 企业采集点
 * @author zhoujianjian
 * @date 2017年12月19日 下午7:18:52
 */
@Controller
@RequestMapping("/companyMeter")
public class CompanyMeterController extends AbstractController{
	
	@Autowired
	private CompanyMeterService companyMeterService;
	
	@Autowired
	private PowerInfoService powerInfoService;
	
	@Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(){
        return "company/collection.html";
    }
	
	/**
	 * 仪表状态列表
	 */
	@ResponseBody
	@RequestMapping("/statusList")
	public R statusList(){
		MeterStatus[] values = MeterStatus.values();
		List<HashMap<String, Object>> list = new ArrayList<>();
		for (MeterStatus meterStatus : values) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("status", meterStatus.getValue());
			map.put("statusName", meterStatus.getDesc());
			list.add(map);
		}
		return R.ok().put("list", list);
	}
	
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("companyMeter:save")
	public R save(@RequestBody CompanyMeterEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		if (! BizUtils.checkLength(bean.getMeterId(), 32)) {
			return R.error("采集点编号不能为空且不得超过32个字");
		}
		boolean exist = companyMeterService.isExist(bean.getMeterId());
		if (exist) {
			return R.error("该采集点编号已录入系统，不可重复录入");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		if (null == bean.getMultiple()) {
			return R.error("倍率不能为空");
		}
		if (! BizUtils.isPositiveInt(String.valueOf(bean.getMultiple()))) {
			return R.error("倍率不合法");
		}
		// 检查采集点名称是否重复
		boolean isNameExist = companyMeterService.isNameExist(bean.getName(), companyId);
		if (isNameExist) {
			return R.error("采集点名称已被使用，请更换采集点名称再次添加");
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_METER_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyMeterService.save(bean);
		return R.ok("新建成功");
	}
	
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("companyMeter:update")
	public R update(@RequestBody CompanyMeterEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		CompanyMeterEntity meterEntity = companyMeterService.queryObject(bean.getId());
		if (null == meterEntity) {
			return R.error("要编辑的采集点不存在");
		}
		if (! BizUtils.checkLength(bean.getMeterId(), 32)) {
			return R.error("采集点编号不能为空且不得超过32个字");
		}
		if (! bean.getMeterId().equals(meterEntity.getMeterId())) {
			return R.error("采集点编号不能修改");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		if (null == bean.getMultiple()) {
			return R.error("倍率不能为空");
		}
		if (! BizUtils.isPositiveInt(String.valueOf(bean.getMultiple()))) {
			return R.error("倍率不合法");
		}
		if (null == bean.getStatus()) {
			return R.error("仪表状态不能为空");
		}
		if (bean.getStatus() != meterEntity.getStatus()) {
			return R.error("仪表状态不能修改");
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_METER_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyMeterService.update(bean);
		return R.ok("修改成功");
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("companyMeter:list")
	public R list(@RequestParam(required = false, defaultValue = "10") Integer limit,
				  @RequestParam(required = false, defaultValue = "1") Integer page,
				  @RequestParam(required = false) String companyName,
				  @RequestParam(required = false) String mobile,
				  @RequestParam(required = false) Integer status){
		HashMap<String, Object> map = new HashMap<>();
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		map.put("companyName", companyName == null ? null : companyName.trim());
		map.put("mobile", mobile == null ? null : mobile.trim());
		map.put("status", status == null ? null :status);
		List<CompanyMeterEntity> list = companyMeterService.queryList(map);
		int total = companyMeterService.queryTotal(map);
		PageUtils pageUtils = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("companyMeter:info")
	public R info(@PathVariable("id") Long id){
		CompanyMeterEntity entity = companyMeterService.queryObject(id);
		return R.ok().put("companyMeter", entity);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("companyMeter:delete")
	public R delete(@RequestBody Long[] ids){
		for (Long id : ids) {
			CompanyMeterEntity meterEntity = companyMeterService.queryObject(id);
			if (null == meterEntity) {
				return R.error( id + "采集点不存在");
			}
			String meterId = meterEntity.getMeterId();
			Long companyId = meterEntity.getCompanyId();
			Integer bindStatus = meterEntity.getBindStatus();
			if (bindStatus.equals(Constants.TAG_YES)) {
				return R.error("编号为" + meterId + "的采集点已绑定线路，不可删除");
			}
			HasDataResult result = powerInfoService.hasData(meterId, companyId);
			if (!result.isSuccess()) {
				return R.error(result.getResultMessage());
			}
			Integer hasData = result.getHasData();
			if (null == hasData) {
				return R.error("系统异常，hasData为空");
			}
			if (!hasData.equals(Constants.TAG_NO)) {
				return R.error("编号为" + meterId + "的采集点已有采集数据，不可删除");
			}
		}
		companyMeterService.deleteBatch(ids);
		return R.ok("删除成功");
	}
	
}
