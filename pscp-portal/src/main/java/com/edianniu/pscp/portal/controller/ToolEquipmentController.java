package com.edianniu.pscp.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.entity.ToolEquipmentEntity;
import com.edianniu.pscp.portal.service.ToolEquipmentService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

@Controller()
@RequestMapping("toolEquipment")
public class ToolEquipmentController {
	
	 private static final Logger logger = LoggerFactory.getLogger(ToolEquipmentController.class);
	 
	@Autowired
	private ToolEquipmentService toolEquipmentService;
	
	@RequestMapping("/toolEquipment.html")
	public String list(){
		
		return "cp/toolEquipment.html";
		
	}
	
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("toolEquipment:list")
	public R list(Integer page, Integer limit){
		try{
			SysUserEntity entity=ShiroUtils.getUserEntity();
			Map<String ,Object>map=new HashMap<String,Object>();
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			Long [] ids=new Long[]{entity.getCompanyId(),0L};
			map.put("ids", ids);
			List<ToolEquipmentEntity>list=toolEquipmentService.queryList(map);
			int count=toolEquipmentService.queryTotal(map);
			PageUtils pageUtil=new PageUtils(list,count,limit,page);
			return R.ok().put("page", pageUtil);
		}catch(Exception e){
			logger.debug("list:{}", e.getMessage());
			return R.error(500,"系统异常");
		}
		
		
	}
	
	
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("toolEquipment:info")
	public R info(@PathVariable("id") Long id){
		ToolEquipmentEntity bean=toolEquipmentService.queryObject(id);
		return R.ok().put("bean", bean);
		
	}
	
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("toolEquipment:save")
	public R save(@RequestBody ToolEquipmentEntity  bean){
		R r=null;
		try{
			SysUserEntity entity=ShiroUtils.getUserEntity();
			bean.setCompanyId(entity.getCompanyId());
			r=toolEquipmentService.save(bean);
			r.put("bean", bean);
		}catch(Exception e){
			logger.debug("save:{}", e.getMessage());
			 r=R.error(500, "系统异常");
			 return r;
		}	
		
		return r;
		
	}
	
	
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("toolEquipment:update")
	public R update (@RequestBody ToolEquipmentEntity  bean){
		R r=null;
		try{
			
			r=toolEquipmentService.update(bean);
			r.put("bean", bean);
		}catch(Exception e){
			logger.debug("update:{}", e.getMessage());
			 r=R.error(500, "系统异常");
			return r;
		}	
		
		return r;
		
	}
	
	
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("toolEquipment:delete")
	public R delete(@RequestBody Long[]ids ){
		
		try{
			toolEquipmentService.deleteBatch(ids);
		}catch(Exception e){
			logger.debug("delete:{}", e.getMessage());
			return  R.error(500, "系统异常");
		}	
		
		return R.ok();
		
	}
}
