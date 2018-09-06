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
import com.edianniu.pscp.cms.entity.MemberMenuEntity;
import com.edianniu.pscp.cms.service.MemberMenuService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cms.utils.RRException;
import com.edianniu.pscp.cms.utils.Constant.MenuType;

/**
 * 会员菜单管理
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午3:59:39 
 * @version V1.0
 */
@RestController
@RequestMapping("/member/menu")
public class MemberMenuController extends AbstractController {
	@Autowired
	private MemberMenuService memberMenuService;
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("member:menu:list")
	public R list(Integer page, Integer limit,String name,String parentName){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("name", name);
		map.put("parentName", parentName);
		
		//查询列表数据
		List<MemberMenuEntity> menuList = memberMenuService.queryList(map);
		int total = memberMenuService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(menuList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("member:menu:select")
	public R select(){
		//查询列表数据
		List<MemberMenuEntity> menuList = memberMenuService.queryNotButtonList();
		
		//添加顶级菜单
		MemberMenuEntity root = new MemberMenuEntity();
		root.setId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 角色授权菜单
	 */
	@RequestMapping("/perms")
	@RequiresPermissions("member:menu:perms")
	public R perms(){
		//查询列表数据
		List<MemberMenuEntity> menuList = memberMenuService.queryList(new HashMap<String, Object>());
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("member:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		MemberMenuEntity menu = memberMenuService.queryObject(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("member:menu:save")
	public R save(@RequestBody MemberMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		memberMenuService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("member:menu:update")
	public R update(@RequestBody MemberMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		memberMenuService.update(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("member:menu:delete")
	public R delete(@RequestBody Long[] menuIds){
		for(Long menuId : menuIds){
			if(menuId.longValue() <= 28){
				return R.error("系统菜单，不能删除");
			}
		}
		memberMenuService.deleteBatch(menuIds);
		
		return R.ok();
	}
	
	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/user")
	public R user(){
		List<MemberMenuEntity> menuList = memberMenuService.getUserMenuList(getUserId());
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(MemberMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			MemberMenuEntity parentMenu = memberMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
