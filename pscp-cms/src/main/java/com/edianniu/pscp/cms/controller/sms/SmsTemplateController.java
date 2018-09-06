package com.edianniu.pscp.cms.controller.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.edianniu.pscp.cms.controller.AbstractController;
import com.edianniu.pscp.cms.entity.SmsTemplateEntity;
import com.edianniu.pscp.cms.service.SmsTemplateService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;



/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 15:22:52
 */
@Controller
@RequestMapping("smstemplate")
public class SmsTemplateController extends AbstractController{
	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private MessageInfoService messageInfoService;
	
	@RequestMapping("/smstemplate.html")
	public String list(){
		return "cp/smstemplate.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("smstemplate:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SmsTemplateEntity> smsTemplateList = smsTemplateService.queryList(map);
		int total = smsTemplateService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(smsTemplateList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smstemplate:info")
	public R info(@PathVariable("id") Long id){
		SmsTemplateEntity smsTemplate = smsTemplateService.queryObject(id);
		
		return R.ok().put("smsTemplate", smsTemplate);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("smstemplate:save")
	public R save(@RequestBody SmsTemplateEntity smsTemplate){
		smsTemplateService.save(smsTemplate);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("smstemplate:update")
	public R update(@RequestBody SmsTemplateEntity smsTemplate){
		try{
			smsTemplateService.update(smsTemplate);
			messageInfoService.clearCache(0L, smsTemplate.getMsgId());
			return R.ok();
		}
		catch(Exception e){
			logger.error("update smsTemplate:{}",e);
		}
		return R.error("系统异常");
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("smstemplate:delete")
	public R delete(@RequestBody Long[] ids){
		smsTemplateService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
