package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

import com.edianniu.pscp.cms.commons.CacheKey;
import com.edianniu.pscp.cms.commons.Constants;
import com.edianniu.pscp.cms.entity.CompanyEquipmentEntity;
import com.edianniu.pscp.cms.service.CompanyEquipmentService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cs.bean.power.EquipmentType;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 客户设备
 * @author zhoujianjian
 * @date 2017年12月19日 下午3:50:25
 */
@Controller
@RequestMapping("/companyEquipment")
public class CompanyEquipmentController extends AbstractController{

	@Autowired
	private CompanyEquipmentService companyEquipmentService;
	
	@Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(){
        return "company/equipment.html";
    }
	
	/**
	 * 1:动力   2:照明     3:空调     4:特殊
	 * 设备类型
	 */
	@ResponseBody
	@RequestMapping("/typeList")
	public R equipmentTypeList(){
		EquipmentType[] values = EquipmentType.values();
		List<HashMap<String, Object>> list = new ArrayList<>();
		for (int i = 1; i < values.length; i++) { //注意要排除第一个类型----综合
			HashMap<String, Object> map = new HashMap<>();
			map.put("type", values[i].getValue());
			map.put("typeName", values[i].getDesc());
			list.add(map);
		}
		return R.ok().put("list", list);
	}
	
	/**
	 * 新建设备
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("companyEquipment:save")
	public R save(@RequestBody CompanyEquipmentEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		if (null != bean.getAddress()) {
			if (! BizUtils.checkLength(bean.getAddress(), 64)) {
				return R.error("地址不能超过64字");
			}
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		if (null == bean.getType()) {
			return R.error("设备类型不能为空");
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_EQUIPMENT_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyEquipmentService.save(bean);
		
		return R.ok("新建成功");
	}
	
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("companyEquipment:update")
	public R update(@RequestBody CompanyEquipmentEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		if (null != bean.getAddress()) {
			if (! BizUtils.checkLength(bean.getAddress(), 64)) {
				return R.error("地址不能超过64字");
			}
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		if (null == bean.getType()) {
			return R.error("设备类型不能为空");
		}
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_EQUIPMENT_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyEquipmentService.update(bean);
		return R.ok("修改成功");
	}
	
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("companyEquipment:info")
	public R info(@PathVariable("id") Long id){
		CompanyEquipmentEntity entity = companyEquipmentService.queryObject(id);
		return R.ok().put("companyEquipment", entity);
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("companyEquipment:list")
	public R queryList(@RequestParam(required = false, defaultValue = "10") Integer limit,
					   @RequestParam(required = false, defaultValue = "1") Integer page,
					   @RequestParam(required = false) String companyName,
					   @RequestParam(required = false) String mobile){
		HashMap<String, Object> map = new HashMap<>();
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		map.put("companyName", companyName == null ? null : companyName.trim());
		map.put("mobile", mobile == null ? null : mobile.trim());
		List<CompanyEquipmentEntity> list = companyEquipmentService.queryList(map);
		if (CollectionUtils.isNotEmpty(list)) {
			for (CompanyEquipmentEntity equipmentEntity : list) {
				equipmentEntity.setName(equipmentEntity.getNameWithAddress());
			}
		}
		int total = companyEquipmentService.queryTotal(map);
		PageUtils pageUtils = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("companyEquipment:delete")
	public R delete(@RequestBody Long[] ids){
		for (Long id : ids) {
			CompanyEquipmentEntity equipmentEntity = companyEquipmentService.queryObject(id);
			if (null == equipmentEntity) {
				return R.error("ID为" + id + "的设备不存在");
			}
			Integer bindStatus = equipmentEntity.getBindStatus();
			if (bindStatus.equals(Constants.TAG_YES)) {
				return R.error("ID为" + id + "的设备已绑定线路，不可删除");
			}
		}
		companyEquipmentService.deleteBatch(ids);
		return R.ok("删除成功");
	}
	
}
