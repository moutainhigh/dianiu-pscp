package com.edianniu.pscp.cms.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edianniu.pscp.cms.controller.AbstractController;
import com.edianniu.pscp.cms.entity.MemberRoleEntity;
import com.edianniu.pscp.cms.service.MemberRoleMenuService;
import com.edianniu.pscp.cms.service.MemberRoleService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;

/**
 * 角色管理
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:21:33
 */
@RestController
@RequestMapping("/member/role")
public class MemberRoleController extends AbstractController {
	@Autowired
	private MemberRoleService memberRoleService;
	@Autowired
	private MemberRoleMenuService memberRoleMenuService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("member:role:list")
	public R list(String name, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<MemberRoleEntity> list = memberRoleService.queryList(map);
		int total = memberRoleService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("member:role:select")
	public R select(){
		//查询列表数据
		List<MemberRoleEntity> list = memberRoleService.queryList(new HashMap<String, Object>());
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("member:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		MemberRoleEntity role = memberRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = memberRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	@RequiresPermissions("member:role:save")
	public R save(@RequestBody MemberRoleEntity role){
		if(StringUtils.isBlank(role.getName())){
			return R.error("角色名称不能为空");
		}
		
		memberRoleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("member:role:update")
	public R update(@RequestBody MemberRoleEntity role){
		if(StringUtils.isBlank(role.getName())){
			return R.error("角色名称不能为空");
		}
		
		memberRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("member:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		memberRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
