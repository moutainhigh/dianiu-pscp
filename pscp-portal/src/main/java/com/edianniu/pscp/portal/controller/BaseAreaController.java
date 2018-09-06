package com.edianniu.pscp.portal.controller;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;
import com.edianniu.pscp.portal.utils.R;


/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-10 09:47:06
 */
@Controller
@RequestMapping("basearea")
public class BaseAreaController {
	@Autowired
	private AreaInfoService areaInfoService;
	
	
	
	
	/**
	 *获取省份下的城市
	 */
	@ResponseBody
	@RequestMapping("/citys")
	@RequiresPermissions("basearea:citys")
	public R getCitysByProvince(Long provinceId){
		if(provinceId!=null){
			List<CityInfo> list = areaInfoService.getCityInfos(provinceId);
			return R.ok().put("citys", list);
		}
		return R.error(401, "provinceId不能为空");
		
	}
	
	/**
	 * 获取所有的省份
	 */
	@ResponseBody
	@RequestMapping("/provinces")
	@RequiresPermissions("basearea:provinces")
	public R provinces(){
		
		List<ProvinceInfo> list = areaInfoService.getProvinceInfos();
		
		return R.ok().put("provinces", list);
	}
	
	/**
	 * 获取所有的省份
	 */
	@ResponseBody
	@RequestMapping("/areas")
	@RequiresPermissions("basearea:areas")
	public R areas( Long cityId){
		if(cityId!=null){
			List<AreaInfo> list = areaInfoService.getAreaInfos(cityId);
			
			return R.ok().put("areas", list);
		}
		return R.error(401, "provinceId不能为空");
		
	}
	
	
}
