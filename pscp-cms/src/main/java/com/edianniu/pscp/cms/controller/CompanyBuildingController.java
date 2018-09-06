package com.edianniu.pscp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.edianniu.pscp.cms.entity.CompanyBuildingEntity;
import com.edianniu.pscp.cms.service.CompanyBuildingService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 企业楼宇
 * @author zhoujianjian
 * @date 2017年12月18日 下午7:42:24
 */
@Controller
@RequestMapping("/companyBuilding")
public class CompanyBuildingController extends AbstractController{
	
	@Autowired
	private CompanyBuildingService companyBuildingService;
	
	@Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(){
        return "company/building.html";
    }
	
	/**
	 * 新建楼宇
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("companyBuilding:save")
	public R save(@RequestBody CompanyBuildingEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		// 检查楼宇名称是否重复
		boolean isNameExist = companyBuildingService.isNameExist(bean.getName(), companyId);
		if (isNameExist) {
			return R.error("楼宇名称已被使用，请更换楼宇名称再次添加");
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_BUILDING_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyBuildingService.save(bean);
		return R.ok("新建成功");
	}
	
	/**
	 * 楼宇编辑
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("companyBuilding:update")
	public R update(@RequestBody CompanyBuildingEntity bean){
		if (null == bean || null == bean.getId() || null == bean.getCompanyId()) {
			return R.error("参数不合法");
		}
		CompanyBuildingEntity entity = companyBuildingService.queryObject(bean.getId());
		if (null == entity || null == entity.getCompanyId()) {
			return R.error("参数不合法");
		}
		if (! entity.getCompanyId().equals(bean.getCompanyId())) {
			return R.error("所属公司不可更改");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_BUILDING_COMPANYID + bean.getCompanyId(), String.valueOf(bean.getCompanyId()), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyBuildingService.update(bean);
		return R.ok("修改成功");
	}
	
	/**
	 * 获取楼宇列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("companyBuilding:list")
	public R queryList(@RequestParam(required = false, defaultValue = "1") Integer page, 
					   @RequestParam(required = false, defaultValue = "10") Integer limit, 
					   @RequestParam(required = false) String companyName, 
					   @RequestParam(required = false) String mobile){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("companyName", companyName == null ? null : companyName.trim());
		map.put("mobile", mobile == null ? null : mobile.trim());
		
		List<CompanyBuildingEntity> list = companyBuildingService.queryList(map);
		int total = companyBuildingService.queryTotal(map);
		PageUtils pageUtils = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 楼宇详情
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("companyBuilding:info")
	public R info(@PathVariable("id") Long id){
		CompanyBuildingEntity entity = companyBuildingService.queryObject(id);
		return R.ok().put("companyBuilding", entity);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("companyBuilding:delete")
	public R delete(@RequestBody Long[] ids){
		for (Long id : ids) {
			CompanyBuildingEntity buildingEntity = companyBuildingService.queryObject(id);
			if (null == buildingEntity) {
				return R.error("ID为" + id + "的楼宇不存在");
			}
			int count = companyBuildingService.getCountOfLines(id);
			if (count > 0) {
				return R.error("ID为" + id + "的楼宇已绑定线路，不可删除");
			}
		}
		companyBuildingService.deleteBatch(ids);
		return R.ok("删除成功");
	}

}
