package com.edianniu.pscp.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.edianniu.pscp.portal.entity.MemberBankCardEntity;
import com.edianniu.pscp.portal.service.MemberBankCardService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;


/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:12:59
 */
@Controller
@RequestMapping("memberbankcard")
public class MemberBankCardController {
	@Autowired
	private MemberBankCardService memberBankCardService;
	
	@RequestMapping("/memberbankcard.html")
	public String list(){
		return "memberbankcard/memberbankcard.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("memberbankcard:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<MemberBankCardEntity> memberBankCardList = memberBankCardService.queryList(map);
		int total = memberBankCardService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(memberBankCardList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("memberbankcard:info")
	public R info(@PathVariable("id") Long id){
		MemberBankCardEntity memberBankCard = memberBankCardService.queryObject(id);
		
		return R.ok().put("memberBankCard", memberBankCard);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("memberbankcard:save")
	public R save(@RequestBody MemberBankCardEntity memberBankCard){
		memberBankCardService.save(memberBankCard);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("memberbankcard:update")
	public R update(@RequestBody MemberBankCardEntity memberBankCard){
		memberBankCardService.update(memberBankCard);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("memberbankcard:delete")
	public R delete(@RequestBody Long[] ids){
		memberBankCardService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
